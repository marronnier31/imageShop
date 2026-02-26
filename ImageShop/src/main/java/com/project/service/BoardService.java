package com.project.service;

import java.util.List;

import com.project.common.domain.PageRequest;
import com.project.domain.Board;

public interface BoardService {

	public int register(Board board) throws Exception;

	public List<Board> list(PageRequest pageRequest) throws Exception;
	
	public Board read(Board board) throws Exception;

	public int modify(Board board) throws Exception;

	public int remove(Board board) throws Exception;

	public int count() throws Exception;

}
