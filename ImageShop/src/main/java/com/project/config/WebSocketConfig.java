package com.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 클라이언트가 연결할 주소
		registry.addEndpoint("/ws-chat").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app"); // 보낼 때: /app/메시지경로
		config.enableSimpleBroker("/topic", "/queue"); // 구독: /topic(전체), /queue(1:1)
		config.setUserDestinationPrefix("/user"); // 1:1 전송용
	}
}
