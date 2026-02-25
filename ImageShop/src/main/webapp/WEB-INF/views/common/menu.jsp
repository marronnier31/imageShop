<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<div class="main-menu" align="right">
	<table>
		<tr>
			<td width="80"><a href="/"><spring:message code="header.home" /></a></td>
			<!-- 로그인을 하지 않은 경우의 메뉴 -->
			<sec:authorize access="!isAuthenticated()">
			</sec:authorize>
			<!-- 인증된 사용자의 메뉴(인가: 관리자, 회원, 매니저) -->
			<sec:authorize access="isAuthenticated()">
				<!-- 인증완료,(인가: 관리자)일때 들어가는 메뉴 -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<!-- 코드그룹관리메뉴 -->
					<td width="120"><a href="/codegroup/list"><spring:message code="menu.codegroup.list" /></a></td>
					<!-- 코드 관리 메뉴 -->
					<td width="120"><a href="/codedetail/list"><spring:message code="menu.codedetail.list" /></a></td>
					<!-- 회원 관리를 메뉴 -->
					<td width="120"><a href="/user/list"><spring:message code="menu.user.admin" /></a></td>
				</sec:authorize>
				<!-- 회원 권한을 가진 사용자인 경우 true -->
				<sec:authorize access="hasRole('ROLE_MEMBER')"></sec:authorize>	
				</sec:authorize>	
		</tr>
	</table>
</div>