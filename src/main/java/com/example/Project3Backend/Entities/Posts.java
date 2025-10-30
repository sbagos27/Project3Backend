package com.example.Project3Backend.Entities;
// not to confuse with api POST but like social media post yk?
public class Posts {

    private int post_id;
    private String post_title;
    private String post_content; //would probably be a description and a video/picture
    private String post_date;
    private String post_author;

    public Posts() {

    }

    public Posts(String post_title, String post_content, String post_date, String post_author) {
        this.post_title = post_title;
        this.post_content = post_content;
        this.post_date = post_date;
        this.post_author = post_author;
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

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
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


}
