package com.blog.controller;

import static com.blog.utils.constants.APIPathConstant.V1_USER_BASE_PATH;

import com.blog.dto.UserDTO;
import com.blog.service.UserService;
import com.blog.utils.constants.CustomHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User APIs", description = "User APIs for blog application.")
@RestController
@RequestMapping(V1_USER_BASE_PATH)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  private final Logger logger = Logger.getLogger(UserController.class);

  @GetMapping("/get-user-info")
  public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
    logger.info("Inside getUser"+request.getAttribute(CustomHeaders.USER_NAME));
    String userName = (String)request.getAttribute(CustomHeaders.USER_NAME);
    UserDTO userDTO = userService.getUserByUserName(userName);
    return ResponseEntity.ok(userDTO);
  }

  @DeleteMapping("/delete-user")
  public ResponseEntity<Void> deleteUser(HttpServletRequest request) {
    logger.info("Inside deleteUser");
    String userName = request.getHeader(CustomHeaders.USER_NAME);
    userService.deleteUser(userName);
    return ResponseEntity.ok().build();
  }
}
