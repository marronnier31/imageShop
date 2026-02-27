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
			<spring:message code="item.header.remove" />
		</h2>
		<!-- 파일 업로드할 때는 반드시 enctype을 사용해야한다. -->
		<form:form modelAttribute="item" action="/item/remove" method="post"
			enctype="multipart/form-data">
			<form:hidden path="itemId" />

			<table class="user_table">
				<tr>
					<td><spring:message code="item.itemName" /></td>
					<td><form:input path="itemName" disabled="true" /></td>
				</tr>
				<tr>
					<td><spring:message code="item.itemPrice" /></td>
					<td><form:input path="price" disabled="true" />&nbsp;원</td>
				</tr>
				<tr>
					<td><spring:message code="item.picture" /></td>
					<td><img src="picture?itemId=${item.itemId}" width="210"></td>
				</tr>
				<tr>
					<td><spring:message code="item.preview" /></td>
					<td><img src="display?itemId=${item.itemId}" width="210"></td>
				</tr>
				<tr>
					<td><spring:message code="item.itemDescription" /></td>
					<td><form:textarea path="description" disabled="true" /></td>
				</tr>

			</table>

			<div class="button-group">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
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
			let formObj = $("#item");

			$("#btnRemove").on("click", function() {
				// 등록은 일반적으로 POST 방식을 사용합니다. 
				// JSP 상단 form 태그에서 method="post"로 수정하는 것을 권장합니다.
				formObj.submit();
			});
			$("#btnList").on("click", function() {
				self.location = "/item/list";
			});
		});
	</script>
</body>
</html>