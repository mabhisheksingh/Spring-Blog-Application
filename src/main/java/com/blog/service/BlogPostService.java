package com.blog.service;

import com.blog.dto.BlogPostDTO;
import com.blog.dto.PagedDTO;
import com.blog.model.BlogPost;
import java.util.List;

public interface BlogPostService {
  BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO);

  @Deprecated
  List<BlogPost> getBlogPostsByAuthor(String authorUserName);

  PagedDTO<BlogPostDTO> getBlogPostList(Integer pageNo, Integer pageSize);
}
