package com.blog.security.idp;

import com.blog.dto.TokenDTO;

public interface BasicAuthFeature {

  TokenDTO login(String username, String password);

  void logout(String username);

  Boolean sessionLogout(String sessionId);
}
