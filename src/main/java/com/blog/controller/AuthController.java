package com.blog.controller;

import static com.blog.utils.constants.APIPathConstant.V1_AUTH_BASE_PATH;

import com.blog.dto.TokenDTO;
import com.blog.dto.UserDTO;
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

  AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@Valid @NotNull @RequestBody LoginRequest loginRequest) {
    logger.info("Inside login");
    logger.info("Login request " + loginRequest);
    TokenDTO loginResponse = authService.login(loginRequest.userName(), loginRequest.password());
    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping(path = "/is-valid-access-token")
  public ResponseEntity<Boolean> isValidAccessToken(
      @NotNull @RequestHeader("access-token") String accessToken) {
    logger.info("Inside isValidAccessToken");
    logger.debug("Login request " + accessToken);
    Boolean response = authService.isValidateToken(accessToken);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<TokenDTO> getRefreshToken(
      @NotNull @RequestHeader("access-token") String accessToken,
      @NotNull @RequestHeader("refresh-token") String refreshToken) {
    logger.info("Inside getRefreshToken");
    TokenDTO response = authService.refreshToken(accessToken, refreshToken);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/get-keyCloak-user-info")
  public ResponseEntity<UserDTO> getUserInfo(@NotNull @RequestParam("userName") String userName) {
    logger.info("Inside getUserInfo");
    UserDTO response = authService.getUserInfo(userName);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout(@NotNull @RequestParam(name = "userName") String userName) {
    logger.info("Inside logout");
    authService.logoutUser(userName);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/logout-session")
  public ResponseEntity<?> logoutSessionId(@NotNull @RequestParam("session-id") String sessionId) {
    logger.info("Inside getUserInfo");
    Boolean response = authService.signOutSession(sessionId);
    return ResponseEntity.ok(response);
  }
}
