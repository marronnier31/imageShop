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
       <h2><spring:message code="codegroup.header.modify" /></h2> 

        <form:form modelAttribute="codeGroup" id="codeGroupForm" action="/codegroup/modify" method="post" >
            <table>
                <tr>
                    <td><spring:message code="codegroup.groupCode" /></td>
                    <td>
                        <form:input path="groupCode" readonly="codegroup.groupCode" />
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="codegroup.groupName" /></td>
                    <td>
                        <form:input path="groupName" />
                    </td>
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
            let formObj = $("#codeGroupForm");

            $("#btnModify").on("click", function() {
            	formObj.submit();
            });
           
            $("#btnList").on("click", function() {
                self.location = "/codegroup/list";
            });
        });
    </script>
</body>
</html>