package com.example.Project3Backend.Entities;

// TODO: Add JPA annotations to make this a database table
// import javax.persistence.Entity;
// import javax.persistence.Id;
// @Entity
public class ChatMessage {
    
    // @Id (etc.)
    private String content;
    private String sender;
    private String recipient;
    // You can add timestamps, messageType, etc.
    public ChatMessage() {
    }
    // ---------------------------------------------

    // (It's also good to have one with all args)
    public ChatMessage(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }
    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    @Override
    public String toString() {
        return "ChatMessage{" +
            "content='" + content + '\'' +
            ", sender='" + sender + '\'' +
            ", recipient='" + recipient + '\'' +
            '}';
    }
}