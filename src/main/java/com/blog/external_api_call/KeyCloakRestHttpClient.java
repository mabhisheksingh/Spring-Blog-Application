package com.blog.external_api_call;

import com.blog.dto.KeyCloakResponseDTO;
import com.blog.utils.constants.KeyCloakConstant;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

public interface KeyCloakRestHttpClient {

  @PostExchange(KeyCloakConstant.VALIDATE_TOKEN_URL)
  KeyCloakResponseDTO isValidAccessToken(
      @RequestHeader(name = "Authorization") String accessToken,
      @RequestBody MultiValueMap<String, String> requestBody);

  @PostExchange(KeyCloakConstant.REFRESH_TOKEN_URL)
  AccessTokenResponse getRefreshToken(@RequestBody MultiValueMap<String, String> requestBody);
}
