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

<div modelAttribute="comment" id="commentForm">
	<input type="hidden" name="boardNo" value="${board.boardNo}" /> <input
		type="hidden" name="commentNo" value="${comment.commentNo}" />
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="customuser" />
	</sec:authorize>

	<table class="data-table" style="width: 80%">
		<c:forEach items="${commentList}" var="comment">
			<tr id="comment-item-${comment.commentNo}">
				<td class="comment-writer">${comment.userId}</td>

				<td class="comment-content">
					<%-- 현재 행의 번호가 URL 파라미터로 넘어온 targetNo와 같으면 수정창 표시 --%> <c:choose>
						<c:when test="${param.targetNo eq comment.commentNo}">
							<form action="/comment/modify" method="post">
								<input type="hidden" name="commentNo"
									value="${comment.commentNo}" /> <input type="hidden"
									name="boardNo" value="${board.boardNo}" />
								<textarea name="content" style="width: 100%">${comment.content}</textarea>
								<div style="text-align: right;">
									<button type="submit">저장</button>
									<%-- 취소 시 targetNo 파라미터를 빼고 다시 호출 --%>
									<a href="/board/read?boardNo=${board.boardNo}">취소</a>
								</div>
							</form>
						</c:when>
						<c:otherwise>
                        ${comment.content}
                    </c:otherwise>
					</c:choose>
				</td>

				<td class="comment-date"><fmt:formatDate
						value="${comment.regDate}" pattern="yyyy-MM-dd" /></td>

				<td><c:if test="${param.targetNo ne comment.commentNo}">
						<%-- 1. 관리자인 경우: 무조건 표시 --%>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a
								href="/board/read?boardNo=${board.boardNo}&targetNo=${comment.commentNo}#comment-item-${comment.commentNo}">수정</a>
							<a
								href="/comment/remove?commentNo=${comment.commentNo}&boardNo=${board.boardNo}">삭제</a>
						</sec:authorize>

						<%-- 2. 일반 회원인 경우: 본인 글인지 확인 --%>
						<sec:authorize access="hasRole('ROLE_MEMBER')">
							<sec:authentication property="principal.username"
								var="currentUserId" />
							<c:if test="${currentUserId eq comment.userId}">
								<%-- 관리자가 아닐 때만 출력되도록 (중복 방지) --%>
								<sec:authorize access="!hasRole('ROLE_ADMIN')">
									<a
										href="/board/read?boardNo=${board.boardNo}&targetNo=${comment.commentNo}#comment-item-${comment.commentNo}">수정</a>
									<a
										href="/comment/remove?commentNo=${comment.commentNo}&boardNo=${board.boardNo}">삭제</a>
								</sec:authorize>
							</c:if>
						</sec:authorize>
					</c:if></td>
			</tr>
		</c:forEach>
	</table>
	</table>
</div>


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