package com.martin.projects.BlogManagement.repository;

import com.martin.projects.BlogManagement.entity.Category;
import com.martin.projects.BlogManagement.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByCategory(Category category);

  List<Post> findAllByContentContainingIgnoreCase(String content);

  List<Post> findByTagsIn(List<String> tags);
}
