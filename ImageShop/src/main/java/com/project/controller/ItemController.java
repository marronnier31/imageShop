package com.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
		// 클라이언트가 보낸 이미지 파일의 정보가 모두 로드가 되었다.
		MultipartFile pictureFile = item.getPicture();
		MultipartFile previewFile = item.getPreview();
		// createdPictureFilename=5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj1.jpg
		// createdPreviewFilename=5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj2.jpg
		String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
		String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
		// 테이블에 저장하기 위한 파일명을 setter
		item.setPictureUrl(createdPictureFilename);
		item.setPreviewUrl(createdPreviewFilename);

		int count = itemService.register(item);

		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/item/list";
	}

	// 상품 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		List<Item> itemList = itemService.list();
		model.addAttribute(itemList);
	}

	// 상품 상세 페이지
	@GetMapping("/read")
	public String read(Item item, Model model) throws Exception {
		model.addAttribute(itemService.read(item));
		return "item/read";
	}

	// 상품 수정 페이지
	@GetMapping("/modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String modifyForm(Item item, Model model) throws Exception {
		model.addAttribute(itemService.read(item));
		return "item/modify";
	}

	// 상품 수정 처리
	@PostMapping("/modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String modify(Item item, RedirectAttributes rttr) throws Exception {
		MultipartFile pictureFile = item.getPicture();

		if (pictureFile != null && pictureFile.getSize() > 0) {
			String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
			item.setPictureUrl(createdFilename);
		}

		MultipartFile previewFile = item.getPreview();

		if (previewFile != null && previewFile.getSize() > 0) {
			String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
			item.setPreviewUrl(createdFilename);
		}
		int count = itemService.modify(item);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/item/list";
	}

	// 상품 삭제화면요청
	@GetMapping("/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeForm(Item item, Model model) throws Exception {
		model.addAttribute(itemService.read(item));
		return "item/remove";
	}

	// 상품 삭제 처리
	@PostMapping(value = "/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String remove(Item item, RedirectAttributes rttr) throws Exception {
		int count = itemService.remove(item);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/item/list";
	}

	// 미리보기(썸네일) 이미지 표시
	@ResponseBody
	@RequestMapping("/display")
	public ResponseEntity<byte[]> displayFile(Integer itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		// 썸네일 이미지 파일명을 디비로부터 가져온다.
		// createdPreviewFilename=5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj2.jpg
		String fileName = itemService.getPreview(itemId);
		try {
			// jpg 확장자명을 가져온다.
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			// jpg의 미디어타입을 리턴받는다. MediaType.IMAGE_JPEG
			MediaType mType = getMediaType(formatName);
			// httpHeader는 서버가 브라우저에게 정보를 담아서 보내주는 객체
			HttpHeaders headers = new HttpHeaders();
			// 파일을 읽는다. inputStream(바이트단위로 읽는다) cf)저장할 때는 outputStream, 2바이트로 읽어올 때는
			// inputReader, 2바이트로 저장할때는 writer
			// D:/upload/5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj.jpg 위치를 찾아서 파일을
			// inputStream으로 읽는다.
			in = new FileInputStream(uploadPath + File.separator + fileName);
			// httpHeader는 서버가 브라우저에게 정보를 담아서 보내주는 객체에 미디어타입을 셋팅
			if (mType != null)
				headers.setContentType(mType);
			// 전송합니다. json방식//HttpStatus.CREATED:완성상태가 아니라 새로 만든 상태란 뜻
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		// 웹브라우저에게 바이트단위로 이미지를 전송한다.
		return entity;
	}

	// 원본 이미지 표시
	@ResponseBody
	@RequestMapping("/picture")
	public ResponseEntity<byte[]> pictureFile(Integer itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		// createdPictureFilename=5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj1.jpg
		String fileName = itemService.getPicture(itemId);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + File.separator + fileName);

			if (mType != null)
				headers.setContentType(mType);

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	// 파일 확장자로 이미지 형식 확인
	private MediaType getMediaType(String formatName) {
		if (formatName != null) {
			if (formatName.equals("JPG"))
				return MediaType.IMAGE_JPEG;
			if (formatName.equals("GIF"))
				return MediaType.IMAGE_GIF;
			if (formatName.equals("PNG"))
				return MediaType.IMAGE_PNG;
		}
		return null;
	}

	// 상품 이미지 파일명과 상품 이미지 데이터를 byte[]타입으로 업로드
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		// 절대로 중복되지않는 아이디를 랜덤생성(5309a8dd-951d-45fa-90d0-b0e30cd7f830)
		UUID uid = UUID.randomUUID();
		// 5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj.jpg
		String createdFileName = uid.toString() + "_" + originalName;
		// D:/upload/5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj.jpg 파일생성=>파일명만있고, 아무것도 안
		// 들어있음
		File target = new File(uploadPath, createdFileName);
		// D:/upload/5309a8dd-951d-45fa-90d0-b0e30cd7f830_kdj.jpg에 데이터를 넣어줘서 똑같이 만들어줌
		FileCopyUtils.copy(fileData, target);
		return createdFileName;
	}
}
