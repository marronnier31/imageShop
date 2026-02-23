<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 

<nav class="main-menu">
    <a href="/user/register">
        <spring:message code="header.joinMember" />
    </a>
    <a href="/codegroup/list">
        <spring:message code="menu.codegroup.list" />
    </a>
</nav>
<hr>