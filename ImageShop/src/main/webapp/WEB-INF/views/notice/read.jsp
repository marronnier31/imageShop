<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop | CodeGroup</title>
<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/codegroup.css">

</head>
<body>
	<!-- jsp:include는 동적처리방식 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container" align="center">
		<h2>
			<spring:message code="notice.header.read" />
		</h2>

		<form:form modelAttribute="notice">
			<form:hidden path="noticeNo" />
			
			<table class="user_table">
				<tr>
					<td><spring:message code="notice.title" /></td>
					<td><form:input path="title" readonly="true" /></td>
					<td><font color="red"><form:errors path="title" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="notice.content" /></td>
					<td><form:textarea path="content" readonly="true" /></td>
					<td><font color="red"><form:errors path="content" /></font></td>
				</tr>
			</table>

			<div class="button-group">
				<!-- 사용자 정보를 가져온다. -->
				<sec:authentication property="principal" var="customuser" />
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<button type="button" id="btnEdit">
						<spring:message code="action.edit" />
					</button>
					<button type="button" id="btnRemove">
						<spring:message code="action.remove" />
					</button>
				</sec:authorize>
				
				<button type="button" id="btnList">
					<spring:message code="action.list" />
				</button>

			</div>
		</form:form>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
		$(document).ready(function() {
			// form의 id를 명시적으로 지정하여 찾는 것이 더 안전합니다.
			let formObj = $("#notice");

			$("#btnEdit").on("click", function() {
				let noticeNo =$("noticeNo").val();
				self.location = "/notice/modify?noticeNo=" +noticeNo;
			});
			$("#btnRemove").on("click", function() {
				let noticeNo =$("noticeNo").val();
				self.location = "/notice/remove?noticeNo=" +noticeNo;
			});
			$("#btnList").on("click", function() {
				self.location = "/notice/list";
			});
		});
	</script>
</body>
</html>