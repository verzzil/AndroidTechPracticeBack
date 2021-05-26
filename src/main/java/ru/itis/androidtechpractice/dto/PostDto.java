package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.Post;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private Integer authorId;
    private String link;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthor().getId())
                .link(post.getImageLink())
                .build();
    }

    public static List<PostDto> from(List<Post> posts) {
        return posts.stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }
}
