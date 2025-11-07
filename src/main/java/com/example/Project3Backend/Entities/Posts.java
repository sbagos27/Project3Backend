package com.example.Project3Backend.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity 
@Table(name = "posts") 
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "created_at") 
    private LocalDateTime createdAt; 

    @Column(name = "caption")
    private String caption;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "likes_count")
    private Long likesCount;

    @Column(name = "comment_count") 
    private Long commentCount;

    // --- Constructors ---

    public Posts() {
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}