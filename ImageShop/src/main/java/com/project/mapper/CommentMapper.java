package com.project.mapper;

import java.util.List;

import com.project.domain.Comment;
import com.project.domain.Notice;

public interface CommentMapper {
	public int create(Comment comment) throws Exception;
	public List<Comment> list() throws Exception;
	public Comment read(Comment comment) throws Exception;
	public int update(Comment comment) throws Exception;
	public int delete(Comment comment) throws Exception;
	
}
