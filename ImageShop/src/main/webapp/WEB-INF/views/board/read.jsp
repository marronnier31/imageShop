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
			<spring:message code="board.header.read" />
		</h2>

		<form:form modelAttribute="board" action="/board/modify" method="get">
			<form:hidden path="boardNo" />
			<table class="user_table">
				<tr>
					<td><spring:message code="board.title" /></td>
					<td><form:input path="title" readonly="true" /></td>
					<td><font color="red"><form:errors path="title" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="board.writer" /></td>
					<td><form:input path="writer" readonly="true" /></td>
					<td><font color="red"><form:errors path="writer" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="board.content" /></td>
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
				<sec:authorize access="hasRole('ROLE_MEMBER')">
					<c:if test="${customuser.username eq board.writer}">
						<button type="button" id="btnEdit">
							<spring:message code="action.edit" />
						</button>
						<button type="button" id="btnRemove">
							<spring:message code="action.remove" />
						</button>
					</c:if>
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
			let formObj = $("#board");

			$("#btnEdit").on("click", function() {
				let boardNo =$("#boardNo");
				self.location = "/board/modify?boardNo="+boardNo.val();
			});
			$("#btnRemove").on("click", function() {
				let boardNo =$("#boardNo");
				self.location = "/board/remove?boardNo="+boardNo.val();
			});
			$("#btnList").on("click", function() {
				self.location = "/board/list";
			});
		});
	</script>
</body>
</html>