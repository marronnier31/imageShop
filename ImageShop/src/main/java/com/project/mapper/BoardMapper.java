package com.project.mapper;

import java.util.List;

import com.project.common.domain.PageRequest;
import com.project.domain.Board;

public interface BoardMapper {

	public int create(Board board) throws Exception;

	public List<Board> list(PageRequest pageRequest) throws Exception;

	public Board read(Board board) throws Exception;

	public int update(Board board) throws Exception;

	public int delete(Board board) throws Exception;

	public int count() throws Exception;


}
