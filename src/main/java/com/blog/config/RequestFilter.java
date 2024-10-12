package com.blog.config;

import com.blog.service.AuthService;
import com.blog.utils.constants.CustomHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    // if (user == null && !request.getRequestURI().equals("/login")) {
    //     // Redirect to login if session is invalid
    //     // response.sendRedirect("/login");
    //     return;
    // }

    // [cbeac751-dfb0-472e-b6c6-8686e5dbb4ba], Granted Authorities: [[OIDC_USER, SCOPE_email,
    // SCOPE_offline_access, SCOPE_openid, SCOPE_profile]], User Attributes:
    // [{at_hash=cvcxJqkJEinAUR-irilWVw, sub=cbeac751-dfb0-472e-b6c6-8686e5dbb4ba,
    // email_verified=true, iss=http://localhost:8080/realms/blog, typ=ID,
    // preferred_username=abhishek, given_name=myuser,
    // nonce=wR_Mxow8ZAlDdTe0UgQNsS-WupfBFaHkiXcUwGTCC1w, sid=c9d16302-ec91-499f-80ed-7a4c0687e0bb,
    // aud=[blog], acr=1, azp=blog, auth_time=2024-10-11T15:08:42Z, name=myuser Admin,
    // exp=2024-10-11T15:17:42Z, family_name=Admin, iat=2024-10-11T15:08:42Z,
    // email=abhishek.r.singh@impetus.com, jti=286e09bf-6399-405a-b9a6-e36aa685951a}]

    // OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // Generate a unique request ID
    String requestId =
        request.getRequestId().isEmpty() ? UUID.randomUUID().toString() : request.getRequestId();
    logger.info("Request ID: " + requestId);

    if (request.getRequestId().isEmpty()) {
      request.setAttribute("Request-Id", requestId);
    }
    // request.setAttribute(CustomHeaders.USER_NAME, oAuth2User.getAttribute("preferred_username"));

    response.addHeader("X-Request-ID", requestId);
    // response.addHeader(CustomHeaders.USER_NAME, oAuth2User.getAttribute("preferred_username"));
    // ((HttpServletResponse) request).addHeader(CustomHeaders.USER_NAME,
    // oAuth2User.getAttribute("preferred_username"));

    filterChain.doFilter(request, response);
  }
}
