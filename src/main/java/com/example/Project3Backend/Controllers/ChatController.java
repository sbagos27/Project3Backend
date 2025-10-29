package com.example.Project3Backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.Project3Backend.Entities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    // This template helps us send messages to specific destinations
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // TODO: Autowire your MessageRepository here for persistence
    // (e.g., public interface MessageRepository extends JpaRepository<ChatMessage, Long>)
    // @Autowired
    // private MessageRepository messageRepository;

    // This method handles all messages sent from clients to the "/app/chat.sendMessage" destination.
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("Incoming STOMP message, sessionId={}, payload={}",
            headerAccessor != null ? headerAccessor.getSessionId() : "-", chatMessage);

        if (chatMessage == null) {
            logger.warn("Received null ChatMessage payload — likely JSON -> object conversion failed");
            return;
        }
        if (chatMessage.getRecipient() == null || chatMessage.getRecipient().isEmpty()) {
            logger.warn("ChatMessage has no recipient: {}", chatMessage);
            return;
        }

        // --- Persistence (Future Step) ---
        // TODO: Save the chatMessage to your Supabase Postgres database

        // --- Routing Logic ---
        try {
            // Send to recipient's queue
            String destination = "/queue/private";
            messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(),
                destination,
                chatMessage
            );
            
            logger.info("Message routed - From: {}, To: {}, Content: {}", 
                       chatMessage.getSender(), 
                       chatMessage.getRecipient(), 
                       chatMessage.getContent());
        } catch (Exception ex) {
            logger.error("Failed to route chat message: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }
}