package com.blog.security.idp;

import com.blog.dto.RegisterUserDTO;

public interface KeyCloakAdminFeatures {
  /**
   * Create a new user in keycloak
   *
   * @param username
   * @param password
   * @param registerUserDTO
   * @return createdId
   */
  String createUser(RegisterUserDTO registerUserDTO);

  Boolean isValidateToken(String token);
  //    Boolean deleteUser(String username);
  //    Boolean updateUser(String username, String password);

}
