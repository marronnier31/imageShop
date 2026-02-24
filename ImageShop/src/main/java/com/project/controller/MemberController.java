package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.domain.CodeLabelValue;
import com.project.domain.Member;
import com.project.service.CodeService;
import com.project.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class MemberController {
	@Autowired
	private MemberService service;

	@Autowired
	private CodeService codeService;

	// 스프링 시큐리티의 비밀번호 암호처리기
	@Autowired
	private PasswordEncoder passwordEncoder;

	// 등록 페이지
	@GetMapping("/register")
	public void registerForm(Member member, Model model) throws Exception {
		// 직업코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);
		model.addAttribute("jobList", jobList);
	}

	// 등록 처리
	@PostMapping("/register")
	public String register(@Validated Member member, BindingResult result, Model model, RedirectAttributes rttr)
			throws Exception {
		if (result.hasErrors()) {
			// 직업코드 목록을 조회하여 뷰에 전달
			String groupCode = "A00";
			List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

			model.addAttribute("jobList", jobList);

			return "user/register";
		}
		// 비밀번호 암호화
		String inputPassword = member.getUserPw();
		member.setUserPw(passwordEncoder.encode(inputPassword));

		int count = service.register(member);
		if(count != 0) {
			rttr.addFlashAttribute("userName", member.getUserName());
			return "redirect:/user/registerSuccess";
		}
		rttr.addFlashAttribute("userName", member.getUserName());
		return "redirect:/user/registerFailed";
	}
	// 등록 성공 페이지 
	@GetMapping("/registerSuccess") 
	public void registerSuccess(Model model) throws Exception { 
	}
	// 등록 실패 페이지 
	@GetMapping("/registerFailed") 
	public void registerFailed(Model model) throws Exception { 
	}
	
}
