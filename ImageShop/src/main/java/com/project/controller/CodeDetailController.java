package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.domain.CodeLabelValue;
import com.project.domain.CodeDetail;
import com.project.service.CodeDetailService;
import com.project.service.CodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/codedetail")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CodeDetailController {
	@Autowired
	private CodeDetailService codeDetailService;

	@Autowired
	private CodeService codeService;

	// 등록 페이지
	@GetMapping("/register")
	public void registerForm(Model model) throws Exception {
		CodeDetail codeDetail = new CodeDetail();
		model.addAttribute(codeDetail);

		// 그룹코드 목록을 조회하여 뷰에 전달
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	// 등록 처리
	@PostMapping("/register")
	public String register(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		int count = codeDetailService.register(codeDetail);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Fail");
		return "redirect:/codedetail/list";
	}

	// 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		model.addAttribute("list", codeDetailService.list());
	}

	// 상세 페이지
	@GetMapping("/read")
	public void read(CodeDetail codeDetail, Model model) throws Exception {
		model.addAttribute(codeDetailService.read(codeDetail));
		// 그룹코드 목록을 조회하여 뷰에 전달
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	// 삭제 처리
	@GetMapping("/remove")
	public String remove(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		int count = codeDetailService.remove(codeDetail);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Fail");
		return "redirect:/codedetail/list";
	}

	// 수정 요청페이지
	@GetMapping("modify")
	public void modifyForm(CodeDetail codeDetail, Model model) throws Exception {
		model.addAttribute(codeDetailService.read(codeDetail));
		// 그룹코드 목록을 조회하여 뷰에 전달
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	// 수정 처리
	@PostMapping("/modify")
	public String modify(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		int count = codeDetailService.modify(codeDetail);
		String message = (count != 0) ? "SUCCESS" : "Modify Fail";
		rttr.addFlashAttribute("msg", message);
		return "redirect:/codedetail/list";
	}
}
