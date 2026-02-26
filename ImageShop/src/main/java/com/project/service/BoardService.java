package com.project.service;

import java.util.List;

import com.project.domain.Board;

public interface BoardService {

	public int register(Board board) throws Exception;

	public List<Board> list() throws Exception;

}
