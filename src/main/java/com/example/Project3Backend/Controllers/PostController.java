package com.example.Project3Backend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @GetMapping("/ping")
    public String ping() {
        return "This is the post controller";
    }

    //TODO
    //more routes and and more thought out Post class
}
