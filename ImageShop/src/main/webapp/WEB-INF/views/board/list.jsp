<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop | CodeGroup</title>
<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/user.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container">
		<div class="list-header">
			<h2>
				<spring:message code="board.header.list" />
			</h2>
			<sec:authorize access="hasRole('ROLE_MEMBER')">
				<a href="/board/register"><spring:message code="action.new" /></a>
			</sec:authorize>
		</div>

		<table class="data-table">
			<tr>
				<th align="center" width="80"><spring:message code="board.no" /></th>
				<th align="center" width="100"><spring:message
						code="board.writer" /></th>
				<th align="center" width="320"><spring:message
						code="board.title" /></th>
				<th align="center" width="180"><spring:message
						code="board.regdate" /></th>
			</tr>
			<c:choose>
				<c:when test="${empty list}">
					<tr>
						<td colspan="4"><spring:message code="common.listEmpty" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${list}" var="board">
						<tr>
							<td align="center">${board.boardNo}</td>
							<td align="right">${board.writer}</td>
							<td align="left"><a
								href='/board/read?boardNo=${board.boardNo}'>${board.title}</a></td>
							<td align="center"><fmt:formatDate
									pattern="yyyy-MM-dd HH:mm" value="${board.regDate}" /></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
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
		} else if (result === "Register Fail") {
			alert("등록처리 실패");
		}
	</script>
</body>
</html>