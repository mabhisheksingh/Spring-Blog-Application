package com.blog.security.idp.impl;

import com.blog.config.KeyCloakConfigProperties;
import com.blog.dto.TokenDTO;
import com.blog.exception.BlogException;
import com.blog.security.idp.BasicAuthFeature;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class keyCloakServiceImpl implements BasicAuthFeature {
  private final Logger logger = Logger.getLogger(keyCloakServiceImpl.class);
  private final Keycloak adminKeyCloak;
  private final KeyCloakConfigProperties keyCloakConfigProperties;

  public keyCloakServiceImpl(KeyCloakConfigProperties keyCloakConfigProperties) {
    this.keyCloakConfigProperties = keyCloakConfigProperties;
    logger.info("HEY : " + keyCloakConfigProperties);
    this.adminKeyCloak =
        KeycloakBuilder.builder()
            .grantType(keyCloakConfigProperties.getGrantType())
            .clientId(
                keyCloakConfigProperties
                    .getClientId()
                    .orElseThrow(
                        () ->
                            new BlogException(
                                "adminKeyCloak.client-id missing in properties file")))
            .clientSecret(
                keyCloakConfigProperties
                    .getClientSecret()
                    .orElseThrow(
                        () ->
                            new BlogException(
                                "adminKeyCloak.client-secret missing in properties file")))
            .realm(keyCloakConfigProperties.getRealm())
            .serverUrl(
                keyCloakConfigProperties
                    .getServerUrl()
                    .orElseThrow(
                        () ->
                            new BlogException(
                                "adminKeyCloak.server-url missing in properties file")))
            .build();
    Keycloak.getInstance(
        "http://localhost:8080/",
        "blog",
        "admin",
        "admin",
        "blog",
        "t3Wf9Mzi1VxL1symNjHH6cFzsfNdSsLr");
  }

  @Override
  public TokenDTO login(String username, String password) {
    try {
      Keycloak kc =
          KeycloakBuilder.builder()
              .grantType(OAuth2Constants.PASSWORD)
              .clientId(
                  keyCloakConfigProperties
                      .getClientId()
                      .orElseThrow(
                          () ->
                              new BlogException(
                                  "adminKeyCloak.client-id missing in properties file")))
              .clientSecret(
                  keyCloakConfigProperties
                      .getClientSecret()
                      .orElseThrow(
                          () ->
                              new BlogException(
                                  "adminKeyCloak.client-secret missing in properties file")))
              .realm(keyCloakConfigProperties.getRealm())
              .serverUrl(
                  keyCloakConfigProperties
                      .getServerUrl()
                      .orElseThrow(
                          () ->
                              new BlogException(
                                  "adminKeyCloak.server-url missing in properties file")))
              .username(username)
              .password(password)
              .build();
      AccessTokenResponse accessTokenResponse = kc.tokenManager().grantToken();
      logger.info("Login successfully" + accessTokenResponse.getIdToken());
      return new TokenDTO(
          accessTokenResponse.getToken(),
          accessTokenResponse.getExpiresIn(),
          accessTokenResponse.getRefreshToken(),
          accessTokenResponse.getRefreshExpiresIn());
    } catch (Exception e) {
      logger.error("Error while login-> ", e);
      throw new BlogException("Error while login" + e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public Boolean logout(String username) {
    return null;
  }

  @Override
  public Object refreshToken(String username, String refreshToken) {
    return null;
  }
}
