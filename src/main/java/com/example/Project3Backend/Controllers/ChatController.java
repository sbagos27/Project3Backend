package com.example.Project3Backend.Controllers;

import com.example.Project3Backend.Entities.ChatMessage;
import com.example.Project3Backend.Repositories.MessageRepository;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RestController;

@Controller 
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {

        System.out.println("--- MESSAGE RECEIVED ---");
        System.out.println("Sender: " + chatMessage.getSender());
        System.out.println("Recipient: " + chatMessage.getRecipient());
        
        chatMessage.setCreatedAt(OffsetDateTime.now());

        try {
            messageRepository.save(chatMessage);
        } catch (Exception e) {
            System.err.println("Failed to save message: " + e.getMessage());
        }

        messagingTemplate.convertAndSendToUser(
            chatMessage.getRecipient(),
            "/queue/private",
            chatMessage
        );
    }

    @GetMapping("/db-check")
    @ResponseBody
    public String checkDatabase() {
        try {
            long count = messageRepository.count();
            return "OK: Connected to Supabase. Found " + count + " messages.";
        } catch (Exception e) {
            return "ERROR: Database connection failed. " + e.getMessage();
        }
    }
}
