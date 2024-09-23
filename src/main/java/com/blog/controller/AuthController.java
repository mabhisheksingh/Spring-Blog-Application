package com.blog.controller;

import static com.blog.utils.constants.APIPathConstant.V1_AUTH_BASE_PATH;

import com.blog.dto.TokenDTO;
import com.blog.external_api_call.KeyCloakRestHttpClient;
import com.blog.model.LoginRequest;
import com.blog.service.AuthService;
import com.blog.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth APIs", description = "Auth APIs for blog application.")
@RestController
@RequestMapping(V1_AUTH_BASE_PATH)
public class AuthController {
  private final Logger logger = Logger.getLogger(AuthController.class);

  private final AuthService authService;
  private final KeyCloakRestHttpClient keyCloakRestHttpClient;

  AuthController(AuthServiceImpl authService, KeyCloakRestHttpClient keyCloakRestHttpClient) {
    this.authService = authService;
    this.keyCloakRestHttpClient = keyCloakRestHttpClient;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@Valid @NotNull @RequestBody LoginRequest loginRequest) {
    logger.info("Inside login");
    logger.info("Login request " + loginRequest);
    TokenDTO loginResponse = authService.login(loginRequest.userName(), loginRequest.password());
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/is-valid-access-token")
  public ResponseEntity<Boolean> login(@NotNull @RequestHeader("access-token") String accessToken) {
    logger.info("Inside login");
    logger.debug("Login request " + accessToken);
    Boolean response = authService.isValidateToken(accessToken);
    return ResponseEntity.ok(response);
  }
}
