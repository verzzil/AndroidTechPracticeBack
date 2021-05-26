package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.Post;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Integer> {
    List<Post> findPostsByAuthor_Id(Integer id);
}
