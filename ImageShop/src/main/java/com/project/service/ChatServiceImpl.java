package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.ChatMessage;
import com.project.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService  {
	@Autowired
    private ChatMapper chatMapper;
	@Override
	public void saveMessage(ChatMessage message) throws Exception{
		chatMapper.insertMessage(message);
	}

}
