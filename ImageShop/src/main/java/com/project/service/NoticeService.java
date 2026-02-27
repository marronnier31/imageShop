package com.project.service;

import java.util.List;

import com.project.domain.Notice;

public interface NoticeService {

	public int register(Notice notice) throws Exception;

	public List<Notice> list() throws Exception;

	public Notice read(Notice notice) throws Exception;

	public int modify(Notice notice) throws Exception;

	public int remove(Notice notice) throws Exception;

}
