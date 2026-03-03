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

<form:form modelAttribute="comment" id="commentForm">
	<input type="hidden" name="boardNo" value="${board.boardNo}" />
	<table class="data-table" style="width: 80%">
		<c:forEach items="${commentList}" var="comment">
			<tr id="comment-item-${comment.commentNo}">
				<td class="comment-writer">${comment.userId}</td>

				<td class="comment-content">${comment.content}</td>

				<td class="comment-date"><fmt:formatDate
						value="${comment.regDate}" pattern="yyyy-MM-dd" /></td>

				<td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a href="/comment/remove?commentNo=${comment.commentNo}&boardNo=${board.boardNo}">삭제</a>
					</sec:authorize> <sec:authentication property="principal" var="customuser" /> 
					<sec:authorize access="hasRole('ROLE_MEMBER')">
						<c:if test="${customuser.username eq comment.userId}">
							<a href="/comment/remove?commentNo=${comment.commentNo}&boardNo=${board.boardNo}">삭제</a>
						</c:if>
					</sec:authorize>
					</td>
			</tr>
		</c:forEach>

	</table>
</form:form>

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

</html>