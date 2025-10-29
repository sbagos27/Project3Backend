package com.example.Project3Backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // This enables WebSocket message handling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable simple memory-based message broker
        registry.enableSimpleBroker("/topic", "/queue", "/user");
        
        // Messages sent to /app/... will be routed to @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
        
        // This is required for user-specific messages
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This is the handshake endpoint with specific allowed origins
        registry.addEndpoint("/ws")
               .setAllowedOrigins("http://localhost:5555", "http://127.0.0.1:5555")
               .setAllowedOriginPatterns("*")
               .withSockJS();
    }
}