package com.project.service;

import java.util.List;

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

	@Override
	public List<Member> list() throws Exception {
		return mapper.list();
	}

	@Override
	public Member read(Member member) throws Exception {
		return mapper.read(member);
	}

	@Override
	@Transactional
	public int modify(Member member) throws Exception {
		int count = mapper.update(member);
		// 회원권한 수정
		int userNo = member.getUserNo();
		// 회원권한 삭제
		if (count != 0) {
			mapper.deleteAuth(userNo);

			List<MemberAuth> authList = member.getAuthList();
			for (int i = 0; i < authList.size(); i++) {
				MemberAuth memberAuth = authList.get(i);
				String auth = memberAuth.getAuth();

				if (auth == null) {
					continue;
				}
				if (auth.trim().length() == 0) {
					continue;
				}
				// 변경된 회원권한 추가
				memberAuth.setUserNo(userNo);
				mapper.modifyAuth(memberAuth);
			}
		}
		return count;
	}
}
