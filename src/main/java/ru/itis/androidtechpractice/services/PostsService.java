package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.PostDto;

import java.util.List;

public interface PostsService {
    List<PostDto> getAllPosts();

    PostDto getPostById(Integer id);

    List<PostDto> getUserPosts(Integer id);

    void createPost(PostDto post);
}
