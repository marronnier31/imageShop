<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Shop</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/home.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <jsp:include page="/WEB-INF/views/common/menu.jsp"/>

    <main class="hero-section">
        <div class="welcome-icon">👋</div>
        
        <h1>
            <spring:message code="common.homeWelcome"/>
        </h1>
        
        <div class="server-time-box">
            📅 서버의 날짜는 <strong>${serverTime}</strong> 입니다.
        </div>
        
        </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>