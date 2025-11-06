package com.example.Project3Backend.Controllers;

import com.example.Project3Backend.Entities.Posts;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // allows frontend access
public class PostController {

    private List<Posts> posts = new ArrayList<>();
    private Map<Integer, List<String>> comments = new HashMap<>();
    private Map<Integer, Integer> likes = new HashMap<>();
    private int idCounter = 1;

    @GetMapping("/ping")
    public String ping() {
        return "This is the Post Controller (Instagram-like API)";
    }

    // CREATE a post
    @PostMapping
    public Posts createPost(@RequestBody Posts post) {
        post.setPost_id(idCounter++);
        posts.add(post);
        comments.put(post.getPost_id(), new ArrayList<>());
        likes.put(post.getPost_id(), 0);
        return post;
    }

    // READ all posts
    @GetMapping
    public List<Posts> getAllPosts() {
        return posts;
    }

    // READ one post by ID
    @GetMapping("/{id}")
    public Posts getPostById(@PathVariable int id) {
        return posts.stream()
                .filter(p -> p.getPost_id() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // UPDATE a post
    @PutMapping("/{id}")
    public Posts updatePost(@PathVariable int id, @RequestBody Posts updated) {
        for (Posts p : posts) {
            if (p.getPost_id() == id) {
                p.setPost_title(updated.getPost_title());
                p.setDescription(updated.getDescription());
                p.setPostImage(updated.getPostImage());
                p.setPost_date(updated.getPost_date());
                p.setPost_author(updated.getPost_author());
                return p;
            }
        }
        throw new RuntimeException("Post not found");
    }

    // DELETE a post
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable int id) {
        posts.removeIf(p -> p.getPost_id() == id);
        comments.remove(id);
        likes.remove(id);
        return "Post deleted successfully";
    }

    // --- SOCIAL ROUTES ---

    // LIKE a post
    @PostMapping("/{id}/like")
    public String likePost(@PathVariable int id) {
        likes.put(id, likes.getOrDefault(id, 0) + 1);
        return "Post " + id + " liked (" + likes.get(id) + " total likes)";
    }

    // GET likes
    @GetMapping("/{id}/likes")
    public int getLikes(@PathVariable int id) {
        return likes.getOrDefault(id, 0);
    }

    // ADD a comment
    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable int id, @RequestBody String comment) {
        comments.computeIfAbsent(id, k -> new ArrayList<>()).add(comment);
        return "Comment added to post " + id;
    }

    // GET comments
    @GetMapping("/{id}/comments")
    public List<String> getComments(@PathVariable int id) {
        return comments.getOrDefault(id, new ArrayList<>());
    }
}
