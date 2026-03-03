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
	<input type="hidden" name="commentNo" value="${comment.commentNo}" />
	<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="customuser"/>
		</sec:authorize>

		<div  class="data-table" style="width: 80%">
    <table>
     <c:choose>
            <c:when test="${empty commentList}">
                <tr>
                    <td colspan="4" class="empty-msg">여러분의 소중한 댓글을 남겨주세요.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${commentList}" var="comment">
                    <tr>
                        <td align="center" style="width:130px;">${comment.userId}</td>

                        <td align="left" class="comment-content-area">
                            <div id="comment-text-${comment.commentNo}">
                                ${comment.content}
                            </div>
                            
                            <form action="/comment/modify" method="post" id="comment-edit-form-${comment.commentNo}" style="display: none; margin: 0;">
                                <input type="hidden" name="commentNo" value="${comment.commentNo}">
                                <input type="hidden" name="boardNo" value="${board.boardNo}">
                                <textarea name="content" class="inline-edit-textarea">${comment.content}</textarea>
                                <div class="inline-edit-btns">
                                    <button type="submit" class="btn-comment-save">저장</button>
                                    <button type="button" class="btn-comment-cancel" onclick="cancelEdit(${comment.commentNo})">취소</button>
                                </div>
                            </form>
                        </td>

                        <td align="center" style="width:140px;">
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${comment.regDate}" />
                        </td>

                        <td class="comment-action-btns" style="width:150px;">
                            <sec:authorize access="isAuthenticated()">
                                <c:if test="${pinfo.username eq comment.userId}">
                                    <div id="action-btns-${comment.commentNo}">
                                        <button type="button" class="btn-comment-modify" onclick="showEditForm(${comment.commentNo})">수정</button>
                                        
                                        <form action="/comment/delete" method="post" style="display: inline-block; margin:0;">
                                            <input type="hidden" name="commentNo" value="${comment.commentNo}">
                                            <input type="hidden" name="boardNo" value="${board.boardNo}">
                                            <button type="submit" class="btn-comment-delete" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                                        </form>
                                    </div>
                                </c:if>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
</div>
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
	function showEditForm(commentNo) {
	    $('.inline-edit-textarea').parent().hide();
	    $('span[id^="comment-text-"]').show();
	    $('.comment-action-btns').show();

	    $('#comment-text-' + commentNo).hide();
	    $('#action-btns-' + commentNo).hide();

	    $('#comment-edit-form-' + commentNo).fadeIn(200);
	}

	function cancelEdit(commentNo) {
	    $('#comment-edit-form-' + commentNo).hide();
	    
	    $('#comment-text-' + commentNo).show();
	    $('#action-btns-' + commentNo).show();
	}
</script>

</html>