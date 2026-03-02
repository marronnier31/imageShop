package com.project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	private int commentNo;
	private int boardNo;
	private String userId;
	private String content;
	private Date regDate;
}
