package com.blog.security.idp.impl;

import static com.blog.utils.constants.KeyCloakHelperFunctions.getKeyCloakPasswordCred;

import com.blog.config.KeyCloakConfigProperties;
import com.blog.dto.KeyCloakResponseDTO;
import com.blog.dto.RegisterUserDTO;
import com.blog.dto.TokenDTO;
import com.blog.dto.UserDTO;
import com.blog.exception.BlogException;
import com.blog.external_api_call.KeyCloakRestHttpClient;
import com.blog.security.idp.BasicAuthFeature;
import com.blog.security.idp.KeyCloakAdminFeatures;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KeyCloakServiceImpl implements BasicAuthFeature, KeyCloakAdminFeatures {
  private final Logger logger = Logger.getLogger(KeyCloakServiceImpl.class);
  private Keycloak adminKeyCloak;
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
  @Transactional
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
  @Transactional
  public void logout(String username) {
    logger.info("inside Logout");
    logger.debug("username ::" + username);
    try {
      List<UserRepresentation> userRepresentations =
          this.adminKeyCloak
              .realm(keyCloakConfigProperties.getRealm())
              .users()
              .search(username, true);
      if (userRepresentations.isEmpty()) {
        throw new BlogException("User not found while logout ", HttpStatus.BAD_REQUEST.value());
      }
      UserRepresentation userRepresentation = userRepresentations.get(0);
      String userId = userRepresentation.getId();
      this.adminKeyCloak.realm(keyCloakConfigProperties.getRealm()).users().get(userId).logout();
    } catch (Exception e) {
      throw new BlogException("Error while logout", HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public Boolean sessionLogout(String sessionId) {
    logger.info("inside Session Logout");
    logger.debug("sessionId ::" + sessionId);
    try {
      this.adminKeyCloak.realm(keyCloakConfigProperties.getRealm()).deleteSession(sessionId, false);
      return Boolean.TRUE;
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }

  @Transactional
  public TokenDTO refreshToken(String accessToken, String refreshToken) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("refresh_token", refreshToken); // This will be URL-encoded
    body.add("client_id", keyCloakConfigProperties.getClientId().get());
    body.add("client_secret", keyCloakConfigProperties.getClientSecret().get());
    body.add("grant_type", OAuth2Constants.REFRESH_TOKEN);
    AccessTokenResponse accessTokenResponse = keyCloakHttpClient.getRefreshToken(body);
    if (Objects.isNull(accessTokenResponse)) {
      throw new BlogException("Error while refreshing token", HttpStatus.BAD_REQUEST.value());
    }
    logger.debug("accessTokenResponse ::" + accessTokenResponse);

    TokenDTO tokenDTO =
        new TokenDTO(
            accessTokenResponse.getToken(),
            accessTokenResponse.getExpiresIn(),
            accessTokenResponse.getRefreshToken(),
            accessTokenResponse.getRefreshExpiresIn());
    logger.debug("tokenDTO ::" + tokenDTO);
    return tokenDTO;
  }

  @Override
  public String createUser(RegisterUserDTO registerUserDTO) {
    // create UserRepresentation
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEmail(registerUserDTO.getEmail());
    userRepresentation.setUsername(registerUserDTO.getUserName());
    userRepresentation.setFirstName(registerUserDTO.getFirstName());
    userRepresentation.setLastName(registerUserDTO.getLastName());
    this.adminKeyCloak = null;
    RealmResource realmResource = this.adminKeyCloak.realm(keyCloakConfigProperties.getRealm());
    UsersResource usersResource = realmResource.users();
    List<String> realmRoles = List.of("offline_access", "uma_authorization", "user");
    userRepresentation.setRealmRoles(realmRoles);

    // Step 3: Assign realm roles (e.g., offline_access, uma_authorization, and custom role)
    RoleRepresentation offlineAccessRole =
        realmResource.roles().get("offline_access").toRepresentation();
    RoleRepresentation umaAuthorizationRole =
        realmResource.roles().get("uma_authorization").toRepresentation();
    RoleRepresentation customRole =
        realmResource.roles().get("user").toRepresentation(); // Custom role

    List<RoleRepresentation> roles = List.of(offlineAccessRole, umaAuthorizationRole, customRole);

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

      // Step 4: Add the roles to the user
      usersResource.get(createdId).roles().realmLevel().add(roles);

      return createdId;
    } catch (RuntimeException e) {
      logger.error("Error while creating user Runtime Exception " + e.getMessage(), e);
      throw new BlogException("Error while creating user", HttpStatus.BAD_REQUEST.value());
    } catch (Exception e) {
      logger.error("Error while creating user exception : " + e.getMessage(), e);
      throw new BlogException("Error while creating user", HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  @Transactional
  public Boolean isValidateToken(String accessToken) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("token", accessToken); // This will be URL-encoded
    body.add("client_id", keyCloakConfigProperties.getClientId().get());
    body.add("client_secret", keyCloakConfigProperties.getClientSecret().get());

    KeyCloakResponseDTO validToken = keyCloakHttpClient.isValidAccessToken(accessToken, body);
    logger.info("validToken ::" + validToken);
    return validToken.isActive();
  }

  @Override
  @Transactional
  public UserDTO getUserInfo(String userName) {
    logger.info("Inside getUserInfo ::");
    List<UserRepresentation> userRepresentation =
        this.adminKeyCloak
            .realm(this.keyCloakConfigProperties.getRealm())
            .users()
            .search(userName, true);
    logger.info("userRepresentation ::" + userRepresentation);
    if (userRepresentation.isEmpty()) {
      throw new BlogException("User not found", HttpStatus.NOT_FOUND.value());
    }

    UserRepresentation userRepresentation2 = userRepresentation.get(0);
    UserDTO userDTO =
        UserDTO.builder()
            .email(userRepresentation2.getEmail())
            .userName(userRepresentation2.getUsername())
            .firstName(userRepresentation2.getFirstName())
            .lastName(userRepresentation2.getLastName())
            .keyCloakUserId(userRepresentation2.getId())
            .build();
    logger.debug("UserTDO from keyCloak ::" + userDTO);
    return userDTO;
  }
}
