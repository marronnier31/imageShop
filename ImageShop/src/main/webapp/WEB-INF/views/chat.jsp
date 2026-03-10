<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>1:1 Chat</title>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body onload="connect()">
	<h2>
		내 아이디: <span id="myId">${username}</span>
	</h2>
	<hr>
	보낼 상대방 ID:
	<input type="text" id="receiverId">
	<br> 메시지:
	<input type="text" id="msgContent">
	<button onclick="send()">전송</button>

	<div id="chatBox"
		style="margin-top: 20px; border: 1px solid #000; height: 200px; overflow-y: auto;">
		<ul id="msgList"></ul>
	</div>

	<script>
		var stompClient = null;
		var sender = "${username}";

		function connect() {
			var socket = new SockJS('/ws-chat');
			stompClient = Stomp.over(socket);

			var myId = "${username}";

			stompClient.connect({}, function(frame) {
				console.log('Connected: ' + frame);

				// [수정 포인트 1] 메시지를 받았을 때 실행할 함수 이름을 showMsg로 유지
				stompClient.subscribe('/topic/chat/' + myId,
						function(response) {
							var data = JSON.parse(response.body);
							showMsg(data.sender + ": " + data.content);
						});
			});
		}

		function send() {
			var receiver = document.getElementById('receiverId').value;
			var content = document.getElementById('msgContent').value;

			var msg = {
				'sender' : sender,
				'receiver' : receiver,
				'content' : content
			};
			stompClient.send("/app/private-message", {}, JSON.stringify(msg));

			// [수정 포인트 2] 내가 보낸 메시지도 showMsg 함수를 쓰도록 변경
			showMsg("나 -> " + receiver + ": " + content);
		}

		// [수정 포인트 3] 함수 이름을 showMsg로 통일 (에러 해결 핵심!)
		function showMsg(text) {
			var li = document.createElement('li');
			li.textContent = text;

			// HTML에 <ul id="msgList"> 가 있는지 꼭 확인하세요!
			var msgList = document.getElementById('msgList');
			if (msgList) {
				msgList.appendChild(li);
				// 메시지가 많아지면 자동으로 아래로 스크롤
				document.getElementById('chatBox').scrollTop = document
						.getElementById('chatBox').scrollHeight;
			}
		}

		// 페이지 로드 시 자동 연결
		connect();
	</script>
</body>
</html>