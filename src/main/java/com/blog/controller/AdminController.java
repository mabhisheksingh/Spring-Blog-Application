package com.blog.controller;

import com.blog.dto.PagedDTO;
import com.blog.dto.UserDTO;
import com.blog.model.BlogPost;
import com.blog.service.AuthService;
import com.blog.service.BlogPostService;
import com.blog.service.UserService;
import com.blog.service.impl.AuthServiceImpl;
import com.blog.utils.constants.APIPathConstant;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(APIPathConstant.V1_ADMIN_BASE_PATH)
public class AdminController {

  private final Logger logger = Logger.getLogger(AdminController.class);

  private final AuthService authService;
  private final BlogPostService blogPostService;
  private final UserService userService;

  AdminController(
      AuthServiceImpl authService, BlogPostService blogPostService, UserService userService) {
    this.authService = authService;
    this.blogPostService = blogPostService;
    this.userService = userService;
  }

  // get by userName
  @GetMapping("/get-blogs-byAuthor")
  public ResponseEntity<?> getMethodName(@RequestParam(required = true) String authorName) {
    logger.info("Getting blogs by author" + authorName);
    List<BlogPost> blogPosts = blogPostService.getBlogPostByAuthor(authorName);
    return ResponseEntity.ok(blogPosts);
  }

  // get all blog list
  @GetMapping("/get-blogs")
  public ResponseEntity<?> getBlogPostList(
      @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
      @RequestParam(defaultValue = "10") @Max(25) @Min(1) Integer pageSize) {
    logger.info("Getting blog post list");
    return ResponseEntity.ok(blogPostService.getBlogPostListByUserName(pageNo, pageSize));
  }

  @GetMapping("/get-user/{userName}")
  public ResponseEntity<?> getUser(@PathVariable String userName) {
    logger.info("Inside getUser");
    UserDTO userDTO = userService.getUserByUserName(userName);
    return ResponseEntity.ok(userDTO);
  }

  // For admin to get all user from Mongo
  @GetMapping("/get-all-user")
  public ResponseEntity<?> getUserList(
      @NotNull @RequestParam(defaultValue = "0", name = "pageNo", required = true) Integer pageNo,
      @NotNull @RequestParam(name = "pageSize", defaultValue = "1", required = true) @Max(50) @Min(1) Integer offset) {
    logger.info("Inside getUserList");
    PagedDTO<UserDTO> userDTOList = userService.getUserList(pageNo, offset);
    return ResponseEntity.ok(userDTOList);
  }
}
