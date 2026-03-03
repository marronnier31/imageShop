package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.security.domain.CustomUser;
import com.project.domain.Comment;
import com.project.domain.Member;
import com.project.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService service;

	@GetMapping("/register")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public void registerForm(Model model, Comment comment, Authentication authentication) throws Exception {
		// 로그인한 사용자 정보 획득
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		// 로그인한 사용자 아이디를 등록 페이지에 표시
		comment.setUserId(member.getUserId());

		model.addAttribute(comment);
	}

	@PostMapping("/register")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public String register(RedirectAttributes rttr, Comment comment) throws Exception {
		int count = service.create(comment);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}

	
	@PostMapping("/modify")
	public String modify(RedirectAttributes rttr, Comment comment) throws Exception {
		int count = service.update(comment);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Failed");
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}
	@GetMapping("/remove")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
	public String remove(RedirectAttributes rttr, Comment comment) throws Exception {
		int count = service.delete(comment);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Failed");
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}

}
