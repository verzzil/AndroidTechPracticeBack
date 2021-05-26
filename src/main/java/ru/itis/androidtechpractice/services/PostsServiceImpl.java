package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.PostDto;
import ru.itis.androidtechpractice.dto.UserDto;
import ru.itis.androidtechpractice.models.Post;
import ru.itis.androidtechpractice.repositories.PostsRepository;
import ru.itis.androidtechpractice.repositories.UsersRepository;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<PostDto> getAllPosts() {
        return PostDto.from(
                postsRepository.findAll()
        );
    }

    @Override
    public PostDto getPostById(Integer id) {
        return PostDto.from(
                postsRepository.findById(id).orElse(Post.builder().build())
        );
    }

    @Override
    public List<PostDto> getUserPosts(Integer id) {
        return PostDto.from(
                postsRepository.findPostsByAuthor_Id(id)
        );
    }

    @Override
    public void createPost(PostDto post) {
        Post newPost = Post.builder()
                .author(usersRepository.findById(post.getAuthorId()).orElseThrow(IllegalArgumentException::new))
                .content(post.getContent())
                .title(post.getTitle())
                .imageLink(post.getLink())
                .build();
        postsRepository.save(newPost);
    }
}
