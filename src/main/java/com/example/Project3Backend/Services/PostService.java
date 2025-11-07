package com.example.Project3Backend.Services;

import com.example.Project3Backend.Entities.Posts;
import com.example.Project3Backend.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; 
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // CREATE
    public Posts createPost(Posts post) {
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    // READ ALL
    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    // READ ONE
    public Posts getPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    // UPDATE
    public Posts updatePost(long id, Posts postDetails) {
        Posts existingPost = getPostById(id);

        existingPost.setCaption(postDetails.getCaption());
        existingPost.setImageUrl(postDetails.getImageUrl());

        return postRepository.save(existingPost);
    }

    // DELETE
    public void deletePost(long id) {
        Posts post = getPostById(id);
        
        postRepository.delete(post);
    }
}