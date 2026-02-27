package com.project.mapper;

import java.util.List;

import com.project.domain.Notice;

public interface NoticeMapper {

	public int create(Notice notice) throws Exception;

	public List<Notice> list() throws Exception;

	public Notice read(Notice notice) throws Exception;

}
