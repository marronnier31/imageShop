package com.project.mapper;

import java.util.List;

import com.project.domain.ChatMessage;

public interface ChatMapper {
	// 메시지 저장
	public int insertMessage(ChatMessage message) throws Exception;

	// 특정 사용자와의 대화 내역 가져오기 (나중에 활용)
	public List<ChatMessage> getChatHistory(String user1, String user2) throws Exception;
}
