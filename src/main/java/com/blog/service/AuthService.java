package com.blog.service;

import com.blog.dto.TokenDTO;
import com.blog.dto.UserDTO;

public interface AuthService {

  TokenDTO login(String username, String password);

  Boolean isValidateToken(String token);

  TokenDTO refreshToken(String accessToken, String refreshToken);

  UserDTO getUserInfo(String accessToken);

  void logoutUser(String userName);

  Boolean signOutSession(String sessionId);
}
