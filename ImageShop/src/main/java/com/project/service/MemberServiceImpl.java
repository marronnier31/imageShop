package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Member;
import com.project.domain.MemberAuth;
import com.project.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mapper;

	@Override
	@Transactional
	public int register(Member member) throws Exception {
		int count = mapper.create(member);
		if (count != 0) {
			// 회원 권한 생성
			MemberAuth memberAuth = new MemberAuth();
			memberAuth.setAuth("ROLE_MEMBER");

			mapper.createAuth(memberAuth);
		}
		return count;
	}
}
