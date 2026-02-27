package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.domain.Notice;
import com.project.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeService service;

	// 공지사항 등록 페이지
	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void registerForm(Model model) throws Exception {
		Notice notice = new Notice();
		model.addAttribute(notice);
	}

	// 공지사항 등록 처리
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String register(Notice notice, RedirectAttributes rttr) throws Exception {
		int count = service.register(notice);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/notice/list";
	}

	// 공지사항 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		model.addAttribute("list", service.list());
	}

	// 공지사항 상세 페이지
	@GetMapping("/read")
	public void read(Notice notice, Model model) throws Exception {
		model.addAttribute(service.read(notice));
	}

	// 공지사항 수정 페이지
	@GetMapping("/modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void modifyForm(Notice notice, Model model) throws Exception {
		model.addAttribute(service.read(notice));
	}

	// 공지사항 수정 처리
	@PostMapping("/modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String modify(Notice notice, RedirectAttributes rttr) throws Exception {
		int count = service.modify(notice);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Modify Failed");
		return "redirect:/notice/list";
	}

	// 공지사항 삭제 처리
	@GetMapping("/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String remove(Notice notice, RedirectAttributes rttr) throws Exception {
		log.info("remove"+notice);
		int count = service.remove(notice);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Failed");
		return "redirect:/notice/list";
	}
}
