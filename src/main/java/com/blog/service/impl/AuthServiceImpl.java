package com.blog.service.impl;

import com.blog.dto.TokenDTO;
import com.blog.security.idp.impl.keyCloakImpl;
import com.blog.service.AuthService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = Logger.getLogger(AuthServiceImpl.class);

    private keyCloakImpl keyCloakImpl;

    public AuthServiceImpl(keyCloakImpl keyCloakImpl) {
        this.keyCloakImpl = keyCloakImpl;
    }

    @Transactional
    @Override
    public TokenDTO login(String username, String password) {
        Object object = keyCloakImpl.login(username.toLowerCase(),password);
        logger.info(object);
        return new TokenDTO();
    }
}
