package com.blog.controller;

import static com.blog.utils.constants.APIPathConstant.V1_BLOG_BASE_PATH;

import com.blog.dto.BlogPostDTO;
import com.blog.model.BlogPost;
import com.blog.service.BlogPostService;
import com.blog.utils.constants.CustomHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Blog", description = "User version 1 API for blog application.")
@RestController
@RequestMapping(V1_BLOG_BASE_PATH)
public class BlogController {

  private final Logger logger = Logger.getLogger(BlogController.class);

  private final BlogPostService blogPostService;

  BlogController(BlogPostService blogPostService) {
    this.blogPostService = blogPostService;
  }

  // create only own blog
  @PostMapping("/create-blog")
  public ResponseEntity<?> createBlog(
      HttpServletRequest request, @NotNull @RequestBody BlogPostDTO blogPostDTO) {
    logger.info("Creating blog");
    String userName = request.getHeader(CustomHeaders.USER_NAME);
    blogPostDTO.setAuthorUserName(userName);
    BlogPostDTO response = blogPostService.createBlogPost(blogPostDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/get-my-blogs")
  public ResponseEntity<?> getBlogsById(HttpServletRequest request) {
    logger.info("Getting blogs by author");
    String userName = request.getHeader(CustomHeaders.USER_NAME);
    List<BlogPost> blogPosts = blogPostService.getBlogPostsByAuthor(userName);
    return ResponseEntity.ok(blogPosts);
  }

  // only get own blog list
  @GetMapping("/get-blogs")
  public ResponseEntity<?> getBlogPostList(
      HttpServletRequest request,
      @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
      @RequestParam(defaultValue = "10") @Max(25) @Min(1) Integer pageSize) {
    logger.info("Getting blog post list");
    String userName = request.getHeader(CustomHeaders.USER_NAME);
    return ResponseEntity.ok(blogPostService.getBlogPostListByUserName(userName, pageNo, pageSize));
  }
}
