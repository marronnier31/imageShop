package com.project.mapper;

import java.util.List;

import com.project.domain.CodeGroup;
import com.project.domain.common.CodeLabelValue;

public interface CodeMapper {
	// 그룹코드 목록 조회 
		public List<CodeLabelValue> getCodeGroupList() throws Exception; 

	// 목록 페이지

	// 상세 페이지

	// 삭제 처리 
	
	// 수정 처리 
}
