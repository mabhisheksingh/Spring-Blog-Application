package com.blog.controller;

import static com.blog.utils.constants.APIPathConstant.V1_AUTH_BASE_PATH;

import com.blog.dto.TokenDTO;
import com.blog.model.LoginRequest;
import com.blog.service.AuthService;
import com.blog.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth APIs", description = "Auth APIs for blog application.")
@RestController
@RequestMapping(V1_AUTH_BASE_PATH)
public class AuthController {
  private final Logger logger = Logger.getLogger(AuthController.class);

  private final AuthService authService;

  AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @NotNull LoginRequest loginRequest) {
    logger.info("Inside login");
    logger.info("Login request " + loginRequest);
    TokenDTO loginResponse = authService.login(loginRequest.userName(), loginRequest.password());
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/login-test")
  public ResponseEntity<?> login() {
    logger.info("Inside login");
    logger.info("Login request ");
    return ResponseEntity.ok("test passed");
  }
}
