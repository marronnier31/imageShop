<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop</title>
</head>
<body>
	<h1>
		<spring:message code="common.homeWelcome"/>
	</h1>
	<p>서버의 날짜는 ${serverTime}</p>
	
</body>
</html>