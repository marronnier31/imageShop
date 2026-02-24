package com.project.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.zeus.common.security.CustomAccessDeniedHandler;
import com.zeus.common.security.CustomLoginSuccessHandler;
import com.zeus.common.security.CustomUserDetailsService;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class SecurityConfig {

	@Autowired 
	DataSource dataSource; 
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("security config");

		// 1.csrf 토큰 비활성화
		httpSecurity.csrf((csrf) -> csrf.disable());

		// 2.security 인가정책
		//Spring Security 6.0 버전부터는 DispatcherType에 대해서도 보안 검사를 수행하는 것이 기본값이 되었다.
		httpSecurity.authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/accessError").permitAll()
                .requestMatchers("/board/**").authenticated() // 게시판: 인증(로그인)
                .requestMatchers("/manager/**").hasRole("MANAGER") // 매니저 기능: 인가(MANAGER)
                .requestMatchers("/admin/**").hasRole("ADMIN") // 매니저 기능: 인가(ADMIN)
                .anyRequest().permitAll() // 그 외 모든 요청은 인증, 인가 필요 없음
        );
		
		
		// 3.접근거부시 예외처리 설정 (/accessError 페이지로 이동)
		//httpSecurity.exceptionHandling(exception -> exception.accessDeniedPage("/accessError"));
		httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(createAccessDeniedHandler()));

		// 4.기본폼 로그인을 활성화
		//httpSecurity.formLogin(Customizer.withDefaults());
//		httpSecurity.formLogin(form -> form
//		        .loginPage("/login")                // 커스텀 로그인 페이지 URL
//		        .loginProcessingUrl("/login")       // 로그인 폼 Action URL (Security가 낚아챔)
//		        //.defaultSuccessUrl("/board/list")	// 성공시 기본 화면 설정
//		        .successHandler(createAuthenticationSuccessHandler())
//		        .permitAll()                        // 로그인 페이지는 누구나 접근 가능해야 함
//		);
		
		// 5.로그아웃처리
//	    httpSecurity.logout(logout -> logout
//	        .logoutUrl("/logout")                // 로그아웃을 처리할 URL (기본값: /logout)
//	        .logoutSuccessUrl("/login")          // 로그아웃 성공 시 이동할 페이지
//	        .invalidateHttpSession(true)         // HTTP 세션 무효화 (기본값: true)
//	        .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 시 관련 쿠키 삭제
//	        .permitAll()                         // 로그아웃 요청은 누구나 접근 가능해야 함
//	    );
	    
	    // 6.자동로그인 기능설정
	    // 데이터 소스를 지정하고 테이블을 이용해서 기존 로그인 정보를 기록 
	    // 쿠키의 유효 시간을 지정한다(24시간).  
//	    httpSecurity.rememberMe(remember -> remember
//	        .key("zeus")                                  // 인증 토큰 생성 시 사용할 키 (보안상 중요)
//	        .tokenRepository(createJDBCRepository())      // DB를 이용한 토큰 저장소 설정
//	        .tokenValiditySeconds(60 * 60 * 24)           // 토큰 유효 기간 (초 단위: 여기서는 24시간)
//	        .userDetailsService(createUserDetailsService())     // 자동 로그인 시 사용자 정보를 조회할 서비스
//	    );
		return httpSecurity.build();
	}

	@Bean 
	 public PasswordEncoder createPasswordEncoder() { 
	  return new BCryptPasswordEncoder(); 
	 }
	
	private PersistentTokenRepository createJDBCRepository() {  
		  JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();  
		  repo.setDataSource(dataSource); 
		  return repo; 
		 } 
	
	//@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(createUserDetailsService()).passwordEncoder(createPasswordEncoder()); 
	}
	
	// 스프링 시큐리티의 UserDetailsService를 구현한 클래스를 빈으로 등록한다.  
	 @Bean 
	 public UserDetailsService createUserDetailsService() {  
	  return new CustomUserDetailsService(); 
	 } 
	
	//3.접근거부시 예외처리 설정을 클래스로 이동한다. 
	@Bean 
	public AccessDeniedHandler createAccessDeniedHandler() { 
		return new CustomAccessDeniedHandler();
	} 

	@Bean 
	 public AuthenticationSuccessHandler createAuthenticationSuccessHandler() { 
	    return new CustomLoginSuccessHandler();
	 }
}
