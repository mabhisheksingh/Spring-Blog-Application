package com.blog.service.impl;

import com.blog.dto.TokenDTO;
import com.blog.exception.BlogException;
import com.blog.security.idp.impl.KeyCloakServiceImpl;
import com.blog.service.AuthService;
import java.util.Objects;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

  Logger logger = Logger.getLogger(AuthServiceImpl.class);

  private final KeyCloakServiceImpl keyCloakServiceImpl;

  public AuthServiceImpl(KeyCloakServiceImpl keyCloakServiceImpl) {
    this.keyCloakServiceImpl = keyCloakServiceImpl;
  }

  @Transactional
  @Override
  public TokenDTO login(String username, String password) {
    TokenDTO object = keyCloakServiceImpl.login(username.toLowerCase(), password);
    logger.info(object);
    return object;
  }

  @Override
  public Boolean isValidateToken(String token) {
    if (Objects.isNull(token)) {
      throw new BlogException("Token is null", HttpStatus.BAD_REQUEST.value());
    }
    return keyCloakServiceImpl.isValidateToken(token);
  }
}
