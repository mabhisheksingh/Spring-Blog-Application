package com.blog.security.idp.impl;

import static com.blog.utils.constants.KeyCloakHelperFunctions.getKeyCloakPasswordCred;

import com.blog.config.KeyCloakConfigProperties;
import com.blog.dto.KeyCloakResponseDTO;
import com.blog.dto.RegisterUserDTO;
import com.blog.dto.TokenDTO;
import com.blog.exception.BlogException;
import com.blog.external_api_call.KeyCloakRestHttpClient;
import com.blog.security.idp.BasicAuthFeature;
import com.blog.security.idp.KeyCloakAdminFeatures;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KeyCloakServiceImpl implements BasicAuthFeature, KeyCloakAdminFeatures {
  private final Logger logger = Logger.getLogger(KeyCloakServiceImpl.class);
  private final Keycloak adminKeyCloak;
  private final KeyCloakConfigProperties keyCloakConfigProperties;
  private final KeyCloakRestHttpClient keyCloakHttpClient;

  public KeyCloakServiceImpl(
      KeyCloakConfigProperties keyCloakConfigProperties,
      KeyCloakRestHttpClient keyCloakRestHttpClient) {
    this.keyCloakConfigProperties = keyCloakConfigProperties;
    this.keyCloakHttpClient = keyCloakRestHttpClient;
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

  public Object refreshToken(String username, String refreshToken) {
    return null;
  }

  @Override
  public String createUser(RegisterUserDTO registerUserDTO) {
    // create UserRepresentation
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEmail(registerUserDTO.getEmail());
    userRepresentation.setUsername(registerUserDTO.getUserName());
    userRepresentation.setFirstName(registerUserDTO.getFirstName());
    userRepresentation.setLastName(registerUserDTO.getLastName());

    // TODO: InFuture Need to verify email address
    userRepresentation.setEmailVerified(true);
    userRepresentation.setEnabled(true);

    userRepresentation.setCredentials(
        List.of(getKeyCloakPasswordCred.apply(registerUserDTO.getPassword())));
    try {
      Response response =
          adminKeyCloak
              .realm(keyCloakConfigProperties.getRealm())
              .users()
              .create(userRepresentation);

      int status = response.getStatus();
      logger.info("User created status " + status);
      String createdId = CreatedResponseUtil.getCreatedId(response);
      if (!HttpStatusCode.valueOf(status).is2xxSuccessful() || createdId == null) {
        throw new BlogException(
            "Failed to create User in Keycloak " + response.getEntity(),
            HttpStatus.BAD_REQUEST.value());
      }
      return createdId;
    } catch (RuntimeException e) {
      logger.error("Error while creating user", e);
      throw new BlogException("Error while creating user", HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public Boolean isValidateToken(String accessToken) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("token", accessToken); // This will be URL-encoded
    body.add("client_id", keyCloakConfigProperties.getClientId().get());
    body.add("client_secret", keyCloakConfigProperties.getClientSecret().get());
    KeyCloakResponseDTO validToken = keyCloakHttpClient.isValidAccessToken(accessToken, body);
    return validToken.isActive();
  }
}
