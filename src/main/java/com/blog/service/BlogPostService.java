package com.blog.service;

import com.blog.dto.BlogPostDTO;
import com.blog.dto.PagedDTO;
import com.blog.model.BlogPost;
import java.util.List;

public interface BlogPostService {
  BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO);

  @Deprecated
  List<BlogPost> getBlogPostByAuthor(String authorUserName);

  PagedDTO<BlogPostDTO> getBlogPostListByUserName(Integer pageNo, Integer pageSize);

  PagedDTO<BlogPostDTO> getBlogPostListByUserName(
      String userName, Integer pageNo, Integer pageSize);

  BlogPostDTO getBlogPostById(String id);

  PagedDTO<BlogPostDTO> searchBlogByTitle(String article, Integer pageNo, Integer pageSize);
}
