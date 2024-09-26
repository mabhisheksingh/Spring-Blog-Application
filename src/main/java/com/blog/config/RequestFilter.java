package com.blog.config;

import static com.blog.utils.constants.APIPathConstant.*;

import com.blog.exception.BlogException;
import com.blog.service.AuthService;
import com.blog.utils.JwtUtility;
import com.blog.utils.constants.CustomHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.apache.http.HttpStatus;
import org.jboss.logging.Logger;
import org.jose4j.jwt.JwtClaims;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class RequestFilter extends OncePerRequestFilter {
  private final Logger logger = Logger.getLogger(RequestFilter.class);

  private final AuthService authService;

  RequestFilter(AuthService authService) {
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String incomingRequestPath = request.getRequestURI();
    String accessToken = request.getHeader(CustomHeaders.ACCESS_TOKEN);
    if (Objects.nonNull(accessToken)) {
      logger.info("Access token: " + accessToken);
      // check it is valid or not
      if (Boolean.FALSE.equals(this.authService.isValidateToken(accessToken))) {
        throw new BlogException("Unauthorized token", HttpStatus.SC_UNAUTHORIZED);
      }
      JwtClaims claims = JwtUtility.parseToken(accessToken);
      logger.info("claims: " + claims);
      try {
        String userName = claims.getClaimValueAsString("preferred_username");
        String roles = claims.getClaimValueAsString("realm_access");
        logger.info("userName: " + userName + " roles: " + roles);
        // For User API
        if (incomingRequestPath.contains(V1_BLOG_BASE_PATH)
            || incomingRequestPath.contains(V1_ADMIN_BASE_PATH)
            || incomingRequestPath.contains(V1_AUTH_BASE_PATH)
            || incomingRequestPath.contains(V1_USER_BASE_PATH)) {
          request.setAttribute("userName", userName);
          if (incomingRequestPath.contains(V1_ADMIN_BASE_PATH) && !roles.contains("admin")) {
            throw new BlogException(
                "Unauthorized access ADMIN only can access this URL", HttpStatus.SC_UNAUTHORIZED);
          } else if (!roles.contains("user")) {
            throw new BlogException("Not Valid User", HttpStatus.SC_UNAUTHORIZED);
          }
        }
      } catch (Exception e) {
        throw new BlogException("Invalid token" + e.getMessage(), HttpStatus.SC_UNAUTHORIZED);
      }
    }
    logger.info("Incoming Request Path: " + incomingRequestPath);

    filterChain.doFilter(request, response);
  }
}
