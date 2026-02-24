<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
			<spring:message code="codegroup.header.modify" />
		</h2>

		<form:form modelAttribute="codeDetail" >
		<form:hidden path="groupCode"/>
			<table>
				<tr>
					<td><spring:message code="codedetail.groupCode" /></td>
					<td><form:select path="groupCode" items="${groupCodeList}"
							itemValue="value" itemLabel="label" disabled="true"/></td>
					<td><font color="red"><form:errors path="groupCode" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="codedetail.codeValue" /></td>
					<td><form:input path="codeValue" readonly="true" /></td>
					<td><font color="red"><form:errors path="codeValue" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="codedetail.codeName" /></td>
					<td><form:input path="codeName" /></td>
					<td><font color="red"><form:errors path="codeName" /></font></td>
				</tr>
			</table>

			<div class="button-group">
				<button type="button" id="btnModify">
					<spring:message code="action.modify" />
				</button>
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
            let formObj = $("#codeDetail");

            $("#btnModify").on("click", function() {
            	formObj.attr("action","/codedetail/modify");
            	formObj.attr("method","post");
            	formObj.submit();
            });
            $("#btnList").on("click", function() {
                self.location = "/codedetail/list";
            });
        });
    </script>
</body>
</html>