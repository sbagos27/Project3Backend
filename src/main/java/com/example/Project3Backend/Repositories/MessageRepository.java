package com.example.Project3Backend.Repositories;

import com.example.Project3Backend.Entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// This interface connects the ChatMessage entity (which maps to 'messages' table)
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
}