<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container">
		<div class="list-header">
			<h2>
				<spring:message code="codegroup.header.list" />
			</h2>
			<a href="/codegroup/register" class="btn-new"> <spring:message
					code="action.new" />
			</a>
		</div>

		<table class="data-table">
			<thead>
				<tr>
					<th width="20%"><spring:message code="codegroup.groupCode" /></th>
					<th width="50%"><spring:message code="codegroup.groupName" /></th>
					<th width="30%"><spring:message code="codegroup.regdate" /></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty list}">
						<tr>
							<td colspan="3" class="text-center empty-msg"><spring:message
									code="common.listEmpty" /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${list}" var="codeGroup">
							<tr>
								<td class="text-center">${codeGroup.groupCode}</td>
								<td><a
									href="/codegroup/read?groupCode=${codeGroup.groupCode}">
										${codeGroup.groupName} </a></td>
								<td class="text-center"><fmt:formatDate
										pattern="yyyy-MM-dd HH:mm" value="${codeGroup.regDate}" /></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
		let result = "${msg}";
		if (result === "SUCCESS") {
			alert("<spring:message code='common.processSuccess' />");
		} else if (result === "Delete Fail") {
			alert("삭제처리 실패");
		} else if (result === "Modify Fail") {
			alert("수정처리 실패");
		}
	</script>
</body>
</html>