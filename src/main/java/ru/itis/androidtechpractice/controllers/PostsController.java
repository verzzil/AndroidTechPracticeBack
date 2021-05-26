package ru.itis.androidtechpractice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.androidtechpractice.dto.PostDto;
import ru.itis.androidtechpractice.services.PostsService;

import java.util.List;

@RestController
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(
                postsService.getAllPosts()
        );
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                postsService.getPostById(id)
        );
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable(name = "userId") Integer id) {
        return ResponseEntity.ok(
                postsService.getUserPosts(id)
        );
    }

    @PostMapping("/posts/create")
    public ResponseEntity<Boolean> createPost(@RequestBody PostDto post) {
        postsService.createPost(post);
        return ResponseEntity.ok(true);
    }
}
