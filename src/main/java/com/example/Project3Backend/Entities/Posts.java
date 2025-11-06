package com.example.Project3Backend.Entities;

import java.util.ArrayList;
import java.util.List;

public class Posts {

    private int post_id; // ID for posts for database
    private String post_title; // maybe wont use this idk
    private String description; // caption text under the image
    private String postImage; // path or URL to image
    private String post_date;
    private String post_author;
    private int likes;
    private List<String> comments;

    public Posts() {
        this.comments = new ArrayList<>();
        this.likes = 0;
    }

    public Posts(String post_title, String description, String postImage, String post_date, String post_author) {
        this.post_title = post_title;
        this.description = description;
        this.postImage = postImage;
        this.post_date = post_date; // will add time.java or whatever that extension is
        this.post_author = post_author;
        this.comments = new ArrayList<>();
        this.likes = 0;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_author() {
        return post_author;
    }

    public void setPost_author(String post_author) {
        this.post_author = post_author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    // convenience methods
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public void like() {
        this.likes++;
    }
}