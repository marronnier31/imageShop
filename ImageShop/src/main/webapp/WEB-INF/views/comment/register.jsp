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


<form:form modelAttribute="comment" action="/comment/register"
	id="commentForm" method="post">
	<input type="hidden" name="boardNo" value="${board.boardNo}" />
	<table class="user_table">
		<td><sec:authentication property="principal.username"
				var="loginId" /> <input type="text" value="${loginId}" name="userId" value="${comment.userId}" readonly />
		</td>
		<tr>
			<td><form:textarea path="content" placeholder="내용을 적으세요" /></td>
			<td><font color="red"><form:errors path="content" /></font></td>
			<td><sec:authorize access="isAuthenticated()">
					<button type="button" id="btnRegister">
						<spring:message code="action.register" />
					</button>
				</sec:authorize></td>
		</tr>
	</table>
</form:form>

<script>
	$(document).ready(function() {
		
		let formObj = $("#commentForm");
		$("#btnRegister").on("click", function() {
			formObj.submit();
		});
	});
</script>

</html>