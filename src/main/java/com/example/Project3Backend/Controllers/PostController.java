package com.example.Project3Backend.Controllers;

import com.example.Project3Backend.Entities.Posts;
import com.example.Project3Backend.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/ping")
    public String ping() {
        return "This is the Post Controller (Instagram-like API)";
    }

    // CREATE a post
    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestBody Posts post) {
        Posts createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // READ all posts
    @GetMapping
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts); // Returns "200 OK"
    }

    // READ posts by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Posts>> getPostsByUserId(@PathVariable Long userId) {
        List<Posts> posts = postService.getPostsByAuthorId(userId);
        return ResponseEntity.ok(posts);
    }

    // READ posts by Cat ID
    @GetMapping("/cat/{catId}")
    public ResponseEntity<List<Posts>> getPostsByCatId(@PathVariable Long catId) {
        List<Posts> posts = postService.getPostsByCatId(catId);
        return ResponseEntity.ok(posts);
    }

    // READ one post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Posts> getPostById(@PathVariable long id) {
        Posts post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // UPDATE a post
    @PutMapping("/{id}")
    public ResponseEntity<Posts> updatePost(@PathVariable long id, @RequestBody Posts postDetails) {
        Posts updatedPost = postService.updatePost(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    // DELETE a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}