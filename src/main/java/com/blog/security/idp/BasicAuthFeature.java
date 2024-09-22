package com.blog.security.idp;

import com.blog.dto.TokenDTO;

public interface BasicAuthFeature {

  TokenDTO login(String username, String password);

  Boolean logout(String username);
}
