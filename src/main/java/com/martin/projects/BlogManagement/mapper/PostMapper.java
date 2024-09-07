package com.martin.projects.BlogManagement.mapper;

import com.martin.projects.BlogManagement.dto.PostRequest;
import com.martin.projects.BlogManagement.dto.PostResponse;
import com.martin.projects.BlogManagement.entity.Category;
import com.martin.projects.BlogManagement.entity.Post;
import java.util.List;

public class PostMapper {

  public static PostResponse toDto(Post post) {
    if (post == null) {
      return null;
    }

    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCategory().name(),
        post.getTags(),
        post.getCreatedAt(),
        post.getUpdatedAt()
    );
  }

  public static List<PostResponse> toListDto(List<Post> posts) {
    if (posts == null) {
      return null;
    }

    return posts.stream()
        .map(PostMapper::toDto)
        .toList();
  }

  public static Post toEntity(PostRequest postRequest) {
    if (postRequest == null) {
      return null;
    }

    Post post = new Post();
    post.setTitle(postRequest.title());
    post.setContent(postRequest.content());
    post.setCategory(Category.valueOf(postRequest.category().toUpperCase()));
    post.setTags(postRequest.tags());

    return post;
  }

  public static void updateEntity(Post oldPost, PostRequest postRequest) {
    if (postRequest == null || oldPost == null) {
      return;
    }

    oldPost.setTitle(postRequest.title());
    oldPost.setContent(postRequest.content());
    oldPost.setCategory(Category.valueOf(postRequest.category().toUpperCase()));
    oldPost.setTags(postRequest.tags());
  }
}
