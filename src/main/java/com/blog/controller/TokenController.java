package com.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// way to check token
@RestController
public class TokenController {

  @GetMapping("/dashboard")
  public ResponseEntity<Model> dashboard(Model model, Authentication authentication) {
    if (authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
      DefaultOidcUser oidcUser = (DefaultOidcUser) oauthToken.getPrincipal();

      // Get ID Token
      String idToken = oidcUser.getIdToken().getTokenValue();
      String accessToken = oidcUser.getAccessTokenHash();

      // Optionally, get claims from ID Token
      String email = oidcUser.getAttribute("email");
      String name = oidcUser.getAttribute("name");

      model.addAttribute("username", name);
      model.addAttribute("email", email);
      model.addAttribute("idToken", idToken);
      model.addAttribute("accessToken", accessToken);
      model.addAttribute("oidcUser", oidcUser);
    }
    return ResponseEntity.ok(model);
  }
}
