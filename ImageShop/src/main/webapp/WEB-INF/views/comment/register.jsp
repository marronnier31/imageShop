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
			method="post">
			<table class="user_table">
				<tr>
					<td><form:textarea path="content" placeholder="내용을 적으세요" /></td>
					<td><font color="red"><form:errors path="content" /></font></td>
				</tr>
			</table>

			<div class="button-group">
			<sec:authorize access="isAuthenticated()">
				<button type="button" id="btnRegister">
					<spring:message code="action.register" />
				</button>
			</sec:authorize>
			</div>
		</form:form>
	

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
		$(document).ready(function() {
			// form의 id를 명시적으로 지정하여 찾는 것이 더 안전합니다.
			let formObj = $("#board");

			$("#btnRegister").on("click", function() {
				// 등록은 일반적으로 POST 방식을 사용합니다. 
				// JSP 상단 form 태그에서 method="post"로 수정하는 것을 권장합니다.
				formObj.submit();
			});
		});
	</script>

</html>