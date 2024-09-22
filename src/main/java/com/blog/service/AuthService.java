package com.blog.service;

import com.blog.dto.TokenDTO;

public interface AuthService {

  TokenDTO login(String username, String password);

  Boolean isValidateToken(String token);
}
