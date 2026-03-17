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
			//엔드포인트연결(전화선 연결)
			let socket = new SockJS('/ws-chat');
			//전화기 역할
			stompClient = Stomp.over(socket);

			let myId = "${username}";
			
			//전화기로 서버에 연결 시도
			stompClient.connect({}, function(frame) {
				console.log('Connected: ' + frame);
				//특정 주소(채널)를 구독하여 주시
				stompClient.subscribe('/topic/chat/' + myId,
						function(response) {
					let data = JSON.parse(response.body);
							showMsg(data.sender + ": " + data.content);
						});
			});
		}

		function send() {
			let receiver = document.getElementById('receiverId').value;
			let content = document.getElementById('msgContent').value;

			let msg = {
				'sender' : sender,
				'receiver' : receiver,
				'content' : content
			};
			//메시지를 상대에게 보냄
			stompClient.send("/app/private-message", {}, JSON.stringify(msg));

			showMsg("나 -> " + receiver + ": " + content);
		}

		function showMsg(text) {
			//ul태그의 msgList안에 li로 메시지를 정렬하기 위해
			let li = document.createElement('li');
			li.textContent = text;

			let msgList = document.getElementById('msgList');
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