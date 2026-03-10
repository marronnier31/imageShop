package com.project.domain;

import lombok.Data;

@Data
public class ChatMessage {
	private Long msgId;       // MSG_ID
    private String sender;    // SENDER
    private String receiver;  // RECEIVER
    private String content;   // CONTENT
    private String sendDate;  // SEND_DATE
    private String readYn;    // READ_YN
}
