package com.project.mapper;

import java.util.List;

import com.project.domain.Board;

public interface BoardMapper {

	public int create(Board board) throws Exception;

	public List<Board> list() throws Exception;

}
