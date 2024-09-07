package com.martin.projects.BlogManagement.service;

import com.martin.projects.BlogManagement.dto.PostRequest;
import com.martin.projects.BlogManagement.dto.PostResponse;
import java.util.List;

public interface PostService {

  List<PostResponse> findAllPosts();

  List<PostResponse> findAllPostsByCategory(String category);

  List<PostResponse> findAllPostsByContent(String content);

  List<PostResponse> findAllPostsByTag(List<String> tags);

  PostResponse findPostById(Long id);

  PostResponse savePost(PostRequest postRequest);

  PostResponse updatePost(Long id, PostRequest postRequest);

  void deletePost(Long id);
}
