package com.blog.service.impl;

import com.blog.dto.BlogPostDTO;
import com.blog.dto.PagedDTO;
import com.blog.exception.BlogException;
import com.blog.model.BlogPost;
import com.blog.repository.BlogPostRepository;
import com.blog.service.BlogPostService;
import java.time.Instant;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogPostServiceImpl implements BlogPostService {

  private final Logger logger = Logger.getLogger(BlogPostServiceImpl.class);

  private final BlogPostRepository blogPostRepository;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
    this.blogPostRepository = blogPostRepository;
  }

  @Override
  public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO) {
    logger.info("Creating blog");
    logger.debug("Blog post DTO " + blogPostDTO);
    try {
      BlogPost blogPost =
          BlogPost.builder()
              .authorUserName(blogPostDTO.getAuthorUserName())
              .content(blogPostDTO.getContent())
              .createdTime(Instant.now())
              .updatedTime(Instant.now())
              .category(blogPostDTO.getCategories())
              .tags(blogPostDTO.getTags())
              .title(blogPostDTO.getTitle())
              .build();

      blogPost = blogPostRepository.save(blogPost);
      blogPostDTO.setId(blogPost.getId());
      blogPostDTO.setCreatedTime(blogPost.getCreatedTime());
      blogPostDTO.setUpdatedTime(blogPost.getUpdatedTime());
      logger.debug("blogPost response " + blogPost);
      return blogPostDTO;
    } catch (Exception e) {
      logger.error("Error creating blog", e);
      throw new BlogException("Error creating blog", HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public List<BlogPost> getBlogPostsByAuthor(String authorUserName) {
    logger.info("Getting blog by author");

    try {
      List<BlogPost> blogPosts = blogPostRepository.findByAuthorUserName(authorUserName);
      logger.info(blogPosts);
      return blogPosts;

    } catch (Exception e) {
      logger.error("Error getting blog by author", e);
      throw new BlogException("Error getting blog by author", HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public PagedDTO<BlogPostDTO> getBlogPostList(Integer pageNo, Integer pageSize) {
    logger.info("Getting blog post list");
    logger.debug("Page no " + pageNo + " Page size " + pageSize);
    try {
      Pageable pageable = PageRequest.of(pageNo, pageSize);
      Page<BlogPost> pageBlogPost = blogPostRepository.findAll(pageable);
      logger.debug("Blog post list " + pageBlogPost);
      List<BlogPostDTO> blogPostDTOList =
          pageBlogPost.getContent().stream()
              .map(
                  blogPost ->
                      BlogPostDTO.builder()
                          .id(blogPost.getId())
                          .authorUserName(blogPost.getAuthorUserName())
                          .content(blogPost.getContent())
                          .createdTime(blogPost.getCreatedTime())
                          .updatedTime(blogPost.getUpdatedTime())
                          .categories(blogPost.getCategory())
                          .tags(blogPost.getTags())
                          .title(blogPost.getTitle())
                          .build())
              .toList();

      logger.debug("Blog post DTO list " + blogPostDTOList);
      PagedDTO<BlogPostDTO> blogPostDTOPagedDTO =
          PagedDTO.<BlogPostDTO>builder()
              .data(blogPostDTOList)
              .currentPage(pageBlogPost.getNumber())
              .totalPages(pageBlogPost.getTotalPages())
              .dataSize(blogPostDTOList.size())
              .totalElements(pageBlogPost.getTotalElements())
              .build();
      logger.debug("Paged DTO " + blogPostDTOPagedDTO);
      return blogPostDTOPagedDTO;
    } catch (Exception e) {
      logger.error("Error getting blog post list", e);
      throw new BlogException("Error getting blog post list", HttpStatus.BAD_REQUEST.value());
    }
  }
}
