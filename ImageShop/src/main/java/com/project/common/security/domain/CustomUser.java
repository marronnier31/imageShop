package com.project.common.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.project.domain.Member;

public class CustomUser extends User{
	private static final long serialVersionUID=1L; 
	private Member member; 

	public CustomUser(String username, @Nullable String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(Member member) {
		// security에 그 정보를 넘겨줘라. 그러면 그 정보를 받고 시큐리티가 인증과 인가를 처리해준다.
		super(member.getUserId(), member.getUserPw(), 
				member.getAuthList().stream().map(auth-> 
				new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		this.member = member; 
	}

	public Member getMember() {
		return member;
	}
}
