package com.blog.service.impl;

import com.blog.dto.TokenDTO;
import com.blog.security.idp.impl.keyCloakServiceImpl;
import com.blog.service.AuthService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

  Logger logger = Logger.getLogger(AuthServiceImpl.class);

  private keyCloakServiceImpl keyCloakServiceImpl;

  public AuthServiceImpl(keyCloakServiceImpl keyCloakServiceImpl) {
    this.keyCloakServiceImpl = keyCloakServiceImpl;
  }

  @Transactional
  @Override
  public TokenDTO login(String username, String password) {
    TokenDTO object = keyCloakServiceImpl.login(username.toLowerCase(), password);
    logger.info(object);
    return object;
  }
}
