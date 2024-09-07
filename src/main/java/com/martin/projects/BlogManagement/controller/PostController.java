package com.martin.projects.BlogManagement.controller;

import com.martin.projects.BlogManagement.dto.PostRequest;
import com.martin.projects.BlogManagement.dto.PostResponse;
import com.martin.projects.BlogManagement.service.PostService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAll(
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String content,
      @RequestParam(required = false) List<String> tags) {

    List<PostResponse> postResponseList;

    if (StringUtils.hasText(category)) {
      postResponseList = postService.findAllPostsByCategory(category);
    } else if (StringUtils.hasText(content)) {
      postResponseList = postService.findAllPostsByContent(content);
    } else if (tags != null && !tags.isEmpty()) {
      postResponseList = postService.findAllPostsByTag(tags);
    } else {
      postResponseList = postService.findAllPosts();
    }

    return ResponseEntity.ok(postResponseList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(postService.findPostById(id));
  }

  @PostMapping
  public ResponseEntity<PostResponse> create(@RequestBody @Valid PostRequest postRequest) {
    PostResponse postCreated = postService.savePost(postRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(postCreated);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostResponse> update(@PathVariable("id") Long id,
      @RequestBody @Valid PostRequest postRequest) {
    PostResponse postUpdated = postService.updatePost(id, postRequest);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(postUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable("id") Long id) {
    postService.deletePost(id);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Post eliminado correctamente");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }
}
