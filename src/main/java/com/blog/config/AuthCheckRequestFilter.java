package com.blog.config;

import static com.blog.utils.constants.APIPathConstant.V1_BLOG_BASE_PATH;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class AuthCheckRequestFilter extends OncePerRequestFilter {
  private final Logger logger = Logger.getLogger(AuthCheckRequestFilter.class.getName());

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String incomingRequestPath = request.getRequestURI();
    logger.info("Incoming Request Path: " + incomingRequestPath);
    if (incomingRequestPath.contains(V1_BLOG_BASE_PATH)) {
      logger.info("Inside blog path");
      String authHeader = request.getHeader("Authorization");
      // do checking token and all things
      logger.info("Auth header: " + authHeader);
    }
    filterChain.doFilter(request, response);
  }
}
