<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop</title>
<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<link rel="stylesheet" href="/css/codegroup.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />
	<div align="center">
		<h2>
			<spring:message code="codegroup.header.register" />
		</h2>
		<form:form modelAttribute="codeGroup" action="/codegroup/register" method="get">
			<table>
				<tr>
					<td><spring:message code="codegroup.groupCode" /></td>
					<td><form:input path="groupCode" /></td>
					<!-- <td> <label for="groupCode">코드그룹</label></td>
					<td><input type="text" name="groupCode" id="groupCode" /></td> -->
					<td><font color="red"><form:errors path="groupCode" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="codegroup.groupName" /></td>
					<td><form:input path="groupName" /></td>
					<td><font color="red"><form:errors path="groupName" /></font></td>
				</tr>
			</table>
		</form:form>

		<div>
			<button type="submit" id="btnRegister">
				<spring:message code="action.register" />
			</button>
			<button type="submit" id="btnList">
				<spring:message code="action.list" />
			</button>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
	<!-- 모든 문서가 로딩이 되면(html코드가 모두 메모리에 로딩이 완료되었으면 or document로 객체가 완료되었으면) -->
		$(document).ready(function() {
			<!-- modelArribute가 id역할도 해서 #을 이용 -->
			<!-- form객체찾기 -->
			let formObj = $("#codeGroup");
			<!-- 등록버튼을 클릭할때 작동되는 핸들러 정의 -->
			$("#btnRegister").on("click", function() {
				<!-- action에 get방식으로 서버로 전송 -->
				formObj.submit();
			});
			<!-- 목록버튼을 클릭할때 작동되는 핸들러 정의 -->
			$("#btnList").on("click", function() {
				<!-- 서버에 페이지 요청, http://localhost:8080/codegroup/list -->	
				self.location = "/codegroup/list";
			});
		});
	</script>
</body>
</html>
