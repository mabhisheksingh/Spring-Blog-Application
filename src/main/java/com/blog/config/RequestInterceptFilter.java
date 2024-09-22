package com.blog.config;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RequestInterceptFilter implements Filter {
  private final Logger logger = Logger.getLogger(RequestInterceptFilter.class);
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // Generate a unique request ID
    String requestId =
        request.getRequestId().isEmpty() ? UUID.randomUUID().toString() : request.getRequestId();
    logger.info("Request ID: " + requestId);
    if (request.getRequestId().isEmpty()) {
      request.setAttribute("Request-Id", requestId);
    }
    // Add the request ID to the response headers
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.addHeader("X-Request-ID", requestId);
    chain.doFilter(request, response);
  }
}
