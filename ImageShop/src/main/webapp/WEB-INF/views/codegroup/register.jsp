<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <spring:message code="codegroup.header.register" />
        </h2>

        <form:form modelAttribute="codeGroup" action="/codegroup/register" method="post">
            <table>
                <tr>
                    <td><spring:message code="codegroup.groupCode" /></td>
                    <td>
                        <form:input path="groupCode" placeholder="코드그룹 입력" />
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="codegroup.groupName" /></td>
                    <td>
                        <form:input path="groupName" placeholder="그룹명 입력" />
                    </td>
                </tr>
            </table>

            <div class="button-group">
                <button type="button" id="btnRegister">
                    <spring:message code="action.register" />
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
            let formObj = $("#codeGroup");

            $("#btnRegister").on("click", function() {
                // 등록은 일반적으로 POST 방식을 사용합니다. 
                // JSP 상단 form 태그에서 method="post"로 수정하는 것을 권장합니다.
                formObj.submit();
            });

            $("#btnList").on("click", function() {
                self.location = "/codegroup/list";
            });
        });
    </script>
</body>
</html>