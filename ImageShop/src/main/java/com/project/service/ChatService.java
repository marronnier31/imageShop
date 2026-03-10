package com.project.service;

import com.project.domain.ChatMessage;

public interface ChatService {
	public void saveMessage(ChatMessage message) throws Exception;
}
