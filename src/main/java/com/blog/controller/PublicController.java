package com.blog.controller;

import com.blog.dto.RegisterUserDTO;
import com.blog.service.BlogPostService;
import com.blog.service.UserService;
import com.blog.utils.constants.APIPathConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public", description = "This is public APIs")
@RequestMapping(APIPathConstant.V1_PUBLIC_BASE_PATH)
@RestController
public class PublicController {

  private final Logger logger = Logger.getLogger(PublicController.class);
  private final UserService userService;
  private final BlogPostService blogPostService;

  PublicController(UserService userService, BlogPostService blogPostService) {
    this.userService = userService;
    this.blogPostService = blogPostService;
  }

  // USER SERVICE

  @PostMapping("/register-user")
  public ResponseEntity<RegisterUserDTO> registerUser(
      @NotNull @Valid @RequestBody RegisterUserDTO registerUserDTO) {
    logger.info("Inside registerUser");
    registerUserDTO = userService.saveUser(registerUserDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(registerUserDTO);
  }

  // BLOG Service
  @GetMapping("/get-blogs")
  public ResponseEntity<?> getBlogPostList(
      @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
      @RequestParam(defaultValue = "10") @Max(25) @Min(1) Integer pageSize) {
    logger.info("Getting blog post list");
    return ResponseEntity.ok(blogPostService.getBlogPostListByUserName(pageNo, pageSize));
  }

  @GetMapping("/search-blogs-by-title")
  public ResponseEntity<?> getBlogByArticle(
      @RequestParam(defaultValue = "") String article,
      @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
      @RequestParam(defaultValue = "10") @Max(25) @Min(1) Integer pageSize) {
    logger.info("get Blogs by Title..");
    return ResponseEntity.ok(blogPostService.searchBlogByTitle(article, pageNo, pageSize));
  }

  @GetMapping("/get-blog-by-id/{id}")
  public ResponseEntity<?> getBlogById(@PathVariable String id) {
    logger.info("Inside getBlogById");
    return ResponseEntity.ok(blogPostService.getBlogPostById(id));
  }
}
