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
		if (count != 0) {
			rttr.addFlashAttribute("userName", member.getUserName());
			return "redirect:/user/registerSuccess";
		}
		rttr.addFlashAttribute("userName", member.getUserName());
		return "redirect:/user/registerFailed";
	}

	// 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		model.addAttribute("list", service.list());
	}

	// 상세 페이지
	@GetMapping("/read")
	public void read(Member member, Model model) throws Exception {
		// 직업코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);

		model.addAttribute("jobList", jobList);
		model.addAttribute(service.read(member));
	}

	// 수정 페이지
	@GetMapping("/modify")
	public void modifyForm(Member member, Model model) throws Exception {
		// 직업코드 목록을 조회하여 뷰에 전달
		String groupCode = "A00";
		List<CodeLabelValue> jobList = codeService.getCodeList(groupCode);
		model.addAttribute("jobList", jobList);
		model.addAttribute(service.read(member));
	}

	// 수정 처리
	@PostMapping("/modify")
	public String modify(Member member, RedirectAttributes rttr) throws Exception {
		int count = service.modify(member);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Modify Failed");
		return "redirect:/user/list";
	}

	// 삭제 처리
	@PostMapping("/remove")
	public String remove(Member member, RedirectAttributes rttr) throws Exception {
		int count = service.remove(member);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Failed");
		return "redirect:/user/list";
	}

	// 최초 관리자를 생성하는 화면을 반환한다.
	@GetMapping("/setup")
	public String setupAdminForm(Member member, Model model) throws Exception {
		// 회원 테이블 데이터 건수를 확인하여 최초 관리자 등록 페이지를 표시한다.
		if (service.countAll() == 0)
			return "user/setup";

		// 회원 테이블에 데이터가 존재하면 최초 관리자를 생성할 수 없으므로 실패 페이지로 이동한다.
		return "user/setupFailure";
	}

	// 회원 테이블에 데이터가 없으면 최초 관리자를 생성한다.
	@PostMapping("/setup")
	public String setupAdmin(Member member, RedirectAttributes rttr) throws Exception {
		// 회원 테이블 데이터 건수를 확인하여 빈 테이블이면 최초 관리자를 생성한다.
		if (service.countAll() == 0) {
			String inputPassword = member.getUserPw();
			member.setUserPw(passwordEncoder.encode(inputPassword));
			member.setJob("00");
			int count = service.setupAdmin(member);
			if(count != 0) {
				rttr.addFlashAttribute("userName", member.getUserName());
				return "redirect:/user/registerSuccess";				
			} else
				rttr.addFlashAttribute("msg", "Delete Failed");
		}
		// 회원 테이블에 데이터가 존재하면 최초 관리자를 생성할 수 없으므로 실패 페이지로 이동한다.
		return "redirect:/user/setupFailure";
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
