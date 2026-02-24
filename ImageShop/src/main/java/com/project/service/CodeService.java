package com.project.service;

import java.util.List;

import com.project.common.domain.CodeLabelValue;

public interface CodeService {
	// 그룹코드 목록 조회 
	public List<CodeLabelValue> getCodeGroupList() throws Exception; 
	// 지정된 그룹코드에 해당하는 코드 목록 조회 
	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception; 
	// 코드디테일 상세 페이지 
	// 코드디테일 삭제 처리 
	// 코드디테일 수정 처리 
}
