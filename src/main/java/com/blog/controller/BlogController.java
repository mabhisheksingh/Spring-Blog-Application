package com.blog.controller;


import com.blog.dto.BlogPostDTO;
import com.blog.model.BlogPost;
import com.blog.service.BlogPostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.blog.utils.constants.APIPathConstant.V1_BLOG_BASE_PATH;

import java.util.List;


@Tag(name = "Blog" ,description = "User version 1 API for blog application.")
@RestController
@RequestMapping(V1_BLOG_BASE_PATH)
public class BlogController {

    private final Logger logger = Logger.getLogger(BlogController.class);

    private final BlogPostService blogPostService;
    BlogController(BlogPostService blogPostService){
        this.blogPostService = blogPostService;
    }

    @PostMapping("/create-blog")
    public ResponseEntity<?> createBlog(@NotNull @RequestBody BlogPostDTO blogPostDTO) {
        logger.info("Creating blog");
        BlogPostDTO response = blogPostService.createBlogPost(blogPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-blogs-byAuthor")
    public ResponseEntity<?> getMethodName(@RequestParam(required = true) String authorName) {
        logger.info("Getting blogs by author"+authorName);
        List<BlogPost> blogPosts = blogPostService.getBlogPostsByAuthor(authorName);
        return ResponseEntity.ok(blogPosts);
    }

    @GetMapping("/get-blogs")
    public ResponseEntity<?> getBlogPostList(@RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
                                             @RequestParam(defaultValue = "10") @Max(25) @Min(1) Integer pageSize) {
        logger.info("Getting blog post list");
        return ResponseEntity.ok(blogPostService.getBlogPostList(pageNo, pageSize));
    }
    
}
