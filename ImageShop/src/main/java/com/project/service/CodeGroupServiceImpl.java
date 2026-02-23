package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.CodeGroup;
import com.project.mapper.CodeGroupMapper;

@Service
public class CodeGroupServiceImpl implements CodeGroupService {
	@Autowired
	private CodeGroupMapper mapper;

	// 등록 처리
	@Override
	@Transactional
	public int register(CodeGroup codeGroup) throws Exception {
		return mapper.create(codeGroup);
	}

	// 목록 페이지
	@Override
	public List<CodeGroup> list() throws Exception {
		return mapper.list();
	}

	// 상세 페이지
	@Override
	public CodeGroup read(CodeGroup codeGroup) throws Exception {
		return mapper.read(codeGroup); 
	}

	// 삭제 처리
	@Override
	@Transactional
	public int remove(CodeGroup codeGroup) throws Exception {
		return mapper.remove(codeGroup);
	}

	// 수정 처리 
	@Override
	@Transactional
	public int modify(CodeGroup codeGroup) throws Exception {
		return mapper.modify(codeGroup);
	}
	
}
