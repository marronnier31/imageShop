package com.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.domain.CodeLabelValue;
import com.project.common.domain.PageRequest;
import com.project.common.domain.Pagination;
import com.project.common.security.domain.CustomUser;
import com.project.domain.Board;
import com.project.domain.Comment;
import com.project.domain.Member;
import com.project.service.BoardService;
import com.project.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private CommentService commentService;

	// 게시글 등록 페이지
	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public void registerForm(Model model, Authentication authentication) throws Exception {
		// 로그인한 사용자 정보 획득
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		Board board = new Board();
		// 로그인한 사용자 아이디를 등록 페이지에 표시
		board.setWriter(member.getUserId());

		model.addAttribute(board);
	}

	// 게시글 등록 처리
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String register(Board board, RedirectAttributes rttr) throws Exception {
		int count = service.register(board);
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Register Failed");
		return "redirect:/board/list";
	}

	// 게시글 목록 페이지
	@GetMapping("/list")
	public void list(Model model, @ModelAttribute("pgrq") PageRequest pageRequest) throws Exception {
		if (pageRequest.getPage() == 0)
			pageRequest = new PageRequest();
		// 요청하는 페이지가 4페이지라면 디비에 가서 31~40 가져온다.
		// 4페이지를 보여주는 기능
		model.addAttribute("list", service.list(pageRequest));
		// 페이지를 보여주는 기능([prev=true]1,2,3,[4],5,6,7,8,9,10[next=true])
		Pagination pagination = new Pagination();
		// 현재페이지 4, 한페이지당 보여주는 갯수 10개로 세팅
		pagination.setPageRequest(pageRequest);
		// 리스트 전체갯수 셋팅하고 다시 계산한다.
		// 페이지 네비게이션 정보에 검색 처리된 게시글 건수를 저장한다(변경).
		pagination.setTotalCount(service.count(pageRequest));
		// 화면 페이지를 보여주는 정보를 제공한다.
		model.addAttribute(pagination);
		// 검색 유형의 코드명과 코드값을 정의한다.
		List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
		searchTypeCodeValueList.add(new CodeLabelValue("n", "---"));
		searchTypeCodeValueList.add(new CodeLabelValue("t", "Title"));
		searchTypeCodeValueList.add(new CodeLabelValue("c", "Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("w", "Writer"));
		searchTypeCodeValueList.add(new CodeLabelValue("tc", "Title OR Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("cw", "Content OR Writer"));
		searchTypeCodeValueList.add(new CodeLabelValue("tcw", "Title OR Content OR Writer"));
		model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
	}

	// 게시글 상세 페이지
	@GetMapping("/read")
	public void read(Board board, @RequestParam(value="targetNo", required=false) Integer targetNo, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
		// 1. 게시글 상세 정보 추가
		Board boardData = service.read(board);
		model.addAttribute("board", boardData);

		// 2. 댓글 목록 추가 (CommentService를 Autowired 해야 함)
		// boardData.getBoardNo()를 사용하여 해당 게시글의 댓글만 가져오도록 서비스 호출
		model.addAttribute("commentList", commentService.list(boardData.getBoardNo()));

		// 3. 댓글 등록을 위한 빈 객체 추가 (form:form modelAttribute="comment"용)
		model.addAttribute("comment", new Comment());

		// 4. [중요] 수정 대상을 모델에 담아 전송
		// JSP에서 ${targetNo}로 접근하거나 ${param.targetNo}를 직접 써도 되지만,
		// 모델에 명시적으로 담아주는 것이 더 깔끔합니다.
		model.addAttribute("targetNo", targetNo);
	}

	// 게시글 수정 페이지
	@GetMapping("/modify")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void modifyForm(Board board, Model model, @ModelAttribute("pgrq") PageRequest pageRequest) throws Exception {
		model.addAttribute(service.read(board));
	}

	// 게시글 수정 처리
	@PostMapping("/modify")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String modify(Board board, RedirectAttributes rttr, PageRequest pageRequest) throws Exception {
		int count = service.modify(board);
		// RedirectAttributes 객체에 일회성 데이터를 지정하여 전달한다.
		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Modify Failed");
		return "redirect:/board/list";
	}

	// 게시글 삭제 처리
	@GetMapping("/remove")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String remove(Board board, RedirectAttributes rttr, PageRequest pageRequest) throws Exception {
		int count = service.remove(board);
		// RedirectAttributes 객체에 일회성 데이터를 지정하여 전달한다.
		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
		if (count != 0)
			rttr.addFlashAttribute("msg", "SUCCESS");
		else
			rttr.addFlashAttribute("msg", "Delete Failed");
		return "redirect:/board/list";
	}
}
