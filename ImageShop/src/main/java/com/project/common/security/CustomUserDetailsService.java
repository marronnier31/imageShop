package com.project.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.common.security.domain.CustomUser;
import com.project.domain.Member;
import com.project.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberMapper memberMapper; 

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		log.info("Load User By UserId: " + userid); 
		
			Member member = null;
			try {
				member = memberMapper.readByUserId(userid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		log.info("queried by member mapper: " + member); 
		 
		return member == null ? null : new CustomUser(member); 
	}

}
