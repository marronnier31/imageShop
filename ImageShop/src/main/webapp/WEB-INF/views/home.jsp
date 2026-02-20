<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop</title>
<!--  
<script type="text/javascript" src="/js/test.js"></script>
<link rel="stylesheet" href="/css/style.css"/>
-->
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/menu.jsp"/>
	<div align="center">
	<h1>
		<spring:message code="common.homeWelcome"/>
	</h1>
	<p>서버의 날짜는 ${serverTime}</p>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>