<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>

<meta charset="UTF-8">

<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<table class="data-table">
	<c:forEach items="${commentList}" var="comment">
		<tr>
			<td align="center">${comment.userId}</td>
			<td align="left">${comment.regDate}</td>
			<td align="right">${comment.content}</td>
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
		</tr>
	</c:forEach>
</table>


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
	$(document).ready(
			function() {
				// form의 id를 명시적으로 지정하여 찾는 것이 더 안전합니다.
				let formObj = $("#board");

				$("#btnEdit").on(
						"click",
						function() {
							let page = $("#page").val();
							let sizePerPage = $("#sizePerPage").val();
							let boardNo = $("#boardNo").val();
							self.location = "/board/read?page=" + page
									+ "&sizePerPage=" + sizePerPage
									+ "&boardNo=" + boardNo;
						});
				$("#btnRemove").on(
						"click",
						function() {
							let page = $("#page").val();
							let sizePerPage = $("#sizePerPage").val();
							let boardNo = $("#boardNo").val();
							self.location = "/board/read?page=" + page
									+ "&sizePerPage=" + sizePerPage
									+ "&boardNo=" + boardNo;
						});
			});

</script>

</html>