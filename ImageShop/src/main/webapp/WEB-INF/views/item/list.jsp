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
<title>Image Shop | Board</title>
<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/board.css">

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container">
		<div class="list-header">
			<h2>
				<spring:message code="item.header.list" />
			</h2>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<a href="/item/register"><spring:message code="action.new" /></a>
			</sec:authorize>
		</div>

		<table class="data-table">
			<tr>
				<th align="center" width="80"><spring:message code="item.itemId" /></th>
				<th align="center" width="320"><spring:message code="item.itemName" /></th>
				<th align="center" width="100"><spring:message code="item.itemPrice" /></th>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<th align="center" width="80"><spring:message code="item.edit" /></th>
					<th align="center" width="80"><spring:message code="item.remove" /></th>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_MEMBER')">
					<th align="center" width="80"><spring:message code="item.read" /></th>
				</sec:authorize>
			</tr>
			<c:choose>
				<c:when test="${empty itemList}">
					<tr>
						<sec:authorize
							access="!hasRole('ROLE_ADMIN') AND !hasRole('ROLE_MEMBER')">
							<td colspan="3"><spring:message code="common.listEmpty" />
							</td>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td colspan="5"><spring:message code="common.listEmpty" />
							</td>
						</sec:authorize>

						<sec:authorize access="hasRole('ROLE_MEMBER')">
							<td colspan="4"><spring:message code="common.listEmpty" />
							</td>
						</sec:authorize>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${itemList}" var="item">
						<tr>
							<td align="center">${item.itemId}</td>
							<td align="left">${item.itemName}</td>
							<td align="right">${item.price}원</td>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td align="center"><a href="/item/modify?itemId=${item.itemId}"><spring:message code="item.edit" /></a></td>
								<td align="center"><a href="/item/remove?itemId=${item.itemId}"><spring:message code="item.remove" /></a></td>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_MEMBER')">
								<td align="center"><a href="read?itemId=${item.itemId}"><spring:message code="item.read" /></a></td>
							</sec:authorize>
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