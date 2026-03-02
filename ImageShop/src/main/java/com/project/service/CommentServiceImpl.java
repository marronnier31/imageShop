package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Comment;
import com.project.domain.Notice;
import com.project.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper mapper;
	
	@Override
	public int create(Comment comment) throws Exception {
		return mapper.create(comment);
	}

	@Override
	public List<Comment> list(int boardNo) throws Exception {
		return mapper.list(boardNo);
	}
	
	@Override
	public Comment read(Comment comment) throws Exception {
		return mapper.read(comment);
	}

	@Override
	public int update(Comment comment) throws Exception {
		return mapper.update(comment);
	}

	@Override
	public int delete(Comment comment) throws Exception {
		return mapper.delete(comment);
	}

}
