package com.project.service;

import java.util.List;

import com.project.domain.Comment;
import com.project.domain.Notice;

public interface CommentService {
	public int create(Comment comment) throws Exception;

	public List<Comment> list(int boardNo) throws Exception;

	public Comment read(Comment comment) throws Exception;
	
	public int update(Comment comment) throws Exception;

	public int delete(Comment comment) throws Exception;

}
