package com.project.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.domain.Item;
import com.project.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Value("${upload.path}")
	private String uploadPath;

	// 상품 등록 페이지
	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void registerForm(Model model) {
		model.addAttribute(new Item());
	}

	// 상품 등록 처리
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String register(Item item, RedirectAttributes rttr) throws Exception {
		MultipartFile pictureFile = item.getPicture();
		MultipartFile previewFile = item.getPreview();

		String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
		String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

		item.setPictureUrl(createdPictureFilename);
		item.setPreviewUrl(createdPreviewFilename);

		int count = itemService.register(item);

		if(count != 0) rttr.addFlashAttribute("msg", "SUCCESS");
		else rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/item/list";
	}

	// 상품 이미지 업로드
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalName;
		File target = new File(uploadPath, createdFileName);
		FileCopyUtils.copy(fileData, target);
		return createdFileName;
	}
}
