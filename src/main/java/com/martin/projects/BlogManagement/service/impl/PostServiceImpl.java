package com.martin.projects.BlogManagement.service.impl;

import com.martin.projects.BlogManagement.dto.PostRequest;
import com.martin.projects.BlogManagement.dto.PostResponse;
import com.martin.projects.BlogManagement.entity.Category;
import com.martin.projects.BlogManagement.entity.Post;
import com.martin.projects.BlogManagement.exception.ResourceNotFoundException;
import com.martin.projects.BlogManagement.mapper.PostMapper;
import com.martin.projects.BlogManagement.repository.PostRepository;
import com.martin.projects.BlogManagement.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  @Autowired
  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<PostResponse> findAllPosts() {
    return PostMapper.toListDto(postRepository.findAll());
  }

  @Transactional(readOnly = true)
  @Override
  public List<PostResponse> findAllPostsByCategory(String category) {
    List<Post> posts = postRepository.findAllByCategory(Category.valueOf(category.toUpperCase()));
    return PostMapper.toListDto(posts);
  }

  @Transactional(readOnly = true)
  @Override
  public List<PostResponse> findAllPostsByContent(String content) {
    List<Post> posts = postRepository.findAllByContentContainingIgnoreCase(content);
    return PostMapper.toListDto(posts);
  }

  @Transactional(readOnly = true)
  @Override
  public List<PostResponse> findAllPostsByTag(List<String> tags) {
    List<Post> posts = postRepository.findByTagsIn(tags);
    return PostMapper.toListDto(posts);
  }

  @Transactional(readOnly = true)
  @Override
  public PostResponse findPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("El Post con el " + id + " no existe."));
    return PostMapper.toDto(post);
  }

  @Override
  public PostResponse savePost(PostRequest postRequest) {
    Post post = PostMapper.toEntity(postRequest);
    Post createdPost = postRepository.save(post);
    return PostMapper.toDto(createdPost);
  }

  @Override
  public PostResponse updatePost(Long id, PostRequest postRequest) {
    Post postExists = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("El Post con el " + id + " no existe."));
    PostMapper.updateEntity(postExists, postRequest);
    Post updatedPost = postRepository.save(postExists);
    return PostMapper.toDto(updatedPost);
  }

  @Override
  public void deletePost(Long id) {
    Post postExists = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("El Post con el " + id + " no existe."));
    postRepository.delete(postExists);
  }
}
