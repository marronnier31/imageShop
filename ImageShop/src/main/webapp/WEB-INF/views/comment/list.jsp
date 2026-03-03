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

				<td><sec:authorize access="hasRole('ROLE_ADMIN')">
						<button type="button" class="btn-modify-view"
							value="${comment.commentNo}">수정</button>
						<button type="button" class="btn-delete"
							value="${comment.commentNo}">삭제</button>
					</sec:authorize> <sec:authentication property="principal" var="customuser" /> <sec:authorize
						access="hasRole('ROLE_MEMBER')">
						<c:if test="${customuser.username eq comment.userId}">
							<button type="button" class="btn-modify-view"
								value="${comment.commentNo}">수정</button>
							<button type="button" class="btn-delete"
								value="${comment.commentNo}">삭제</button>
						</c:if>
					</sec:authorize></td>
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
	$(document).ready(function() {
	    // 1. [수정] 버튼 클릭 시 수정 모드로 전환
	    $(document).on("click", ".btn-modify-view", function() {
	        let parentRow = $(this).closest("tr");
	        let commentNo = $(this).val(); 
            // .text() 대신 .html()을 사용하여 태그 구조를 가져오거나, 
            // 원래 내용을 정확히 가져오기 위해 .trim() 사용
	        let originContent = parentRow.find(".comment-content").text().trim(); 

	        // 기존 내용을 textarea로 교체 (textarea 안에 originContent를 넣어줌)
	        let editHtml = `
	            <textarea class="edit-textarea" style="width:100%">${originContent}</textarea>
	            <div class="edit-buttons">
	                <button type="button" class="btn-save" value="${comment.commentNo}">저장</button>
	                <button type="button" class="btn-cancel" data-content="${originContent}">취소</button>
	            </div>
	        `;

	        parentRow.find(".comment-content").html(editHtml);
	        $(this).hide(); // 수정 버튼 숨기기
	    });

	    // 2. [취소] 버튼 클릭 시 원래대로 복구
	    $(document).on("click", ".btn-cancel", function() {
	        let parentRow = $(this).closest("tr");
	        let originContent = $(this).data("content");

	        parentRow.find(".comment-content").text(originContent);
	        parentRow.find(".btn-modify-view").show(); // 수정 버튼 다시 표시
	    });

	    // 3. [저장] 버튼 클릭 시
	    $(document).on("click", ".btn-save", function() {
	        let commentNo = $(this).val();
            // 저장 버튼이 있는 행의 textarea 값을 가져옴
	        let newContent = $(this).closest("div").prev(".edit-textarea").val(); 

	        if(!newContent) {
	            alert("내용을 입력하세요.");
	            return;
	        }
            
            // 수정 처리 함수 호출
	        submitUpdate(commentNo, newContent);
	    });
	});

	function submitUpdate(no, content) {
        // 동적 폼 생성 및 제출
	    let form = $('<form action="/comment/modify" method="post"></form>');
	    form.append($('<input type="hidden" name="commentNo">').val(no));
	    form.append($('<input type="hidden" name="content">').val(content));
        // 댓글이 속한 게시글 번호 전달
	    form.append($('<input type="hidden" name="boardNo">').val("${board.boardNo}")); 

	    $('body').append(form);
	    form.submit();
	}
</script>

</html>