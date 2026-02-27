package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Notice;
import com.project.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper mapper;

	@Override
	public int register(Notice notice) throws Exception {
		return mapper.create(notice);
	}

	@Override
	public List<Notice> list() throws Exception {
		return mapper.list();
	}

	@Override
	public Notice read(Notice notice) throws Exception {
		return mapper.read(notice);
	}

	@Override
	public int modify(Notice notice) throws Exception {
		return mapper.update(notice);
	}

	@Override
	public int remove(Notice notice) throws Exception {
		return mapper.delete(notice);
	}
}
