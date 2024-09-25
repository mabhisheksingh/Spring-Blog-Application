package com.blog.security.idp;

import com.blog.dto.RegisterUserDTO;
import com.blog.dto.TokenDTO;

public interface KeyCloakAdminFeatures {
  /**
   * Create a new user in keycloak
   *
   * @param {@link RegisterUserDTO}
   * @return createdId
   */
  String createUser(RegisterUserDTO registerUserDTO);

  Boolean isValidateToken(String token);

  TokenDTO refreshToken(String accessToken, String refreshToken);

  Object getUserInfo(String accessToken);
  //    Boolean deleteUser(String username);
  //    Boolean updateUser(String username, String password);

}
