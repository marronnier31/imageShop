package com.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.domain.ChatMessage;
import com.project.service.ChatService;

@Controller
public class ChatController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private ChatService chatService;

	@MessageMapping("/private-message")
	public void processMessage(@Payload ChatMessage message) throws Exception {
		// 1. DB에 먼저 저장 (오라클 CHAT_MESSAGE 테이블)
		chatService.saveMessage(message);

		// 1:1 전용 주소 대신, 두 사람이 약속한 경로로 직접 전송
	    // 예: /topic/chat/user_55 (받는 사람 아이디를 경로에 포함)
	    messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiver(), message);
	}

	// 채팅 페이지 호출
	@GetMapping("/chat")
	public String chatPage(Model model, Principal principal) {
		// 시큐리티 연동 시 principal.getName() 사용, 테스트 시에는 임의의 이름
		String username = (principal != null) ? principal.getName() : "User_" + (int) (Math.random() * 100);
		model.addAttribute("username", username);
		return "chat";
	}
}
