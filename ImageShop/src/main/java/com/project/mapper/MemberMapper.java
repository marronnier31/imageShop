package com.project.mapper;

import java.util.List;

import com.project.domain.Member;
import com.project.domain.MemberAuth;

public interface MemberMapper {

	public int create(Member member) throws Exception;

	public void createAuth(MemberAuth memberAuth) throws Exception;

	public List<Member> list() throws Exception;

	public Member read(Member member) throws Exception;

	public int update(Member member);

	public void deleteAuth(int userNo);

	public void modifyAuth(MemberAuth memberAuth);

}
