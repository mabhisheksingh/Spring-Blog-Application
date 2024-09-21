package com.blog.security.idp.impl;

import com.blog.security.idp.BasicAuthFeature;
import org.springframework.stereotype.Component;

@Component
public class keyCloakImpl implements BasicAuthFeature {


    @Override
    public Object login(String username, String password) {
        return null;
    }

    @Override
    public Boolean logout(String username) {
        return null;
    }

    @Override
    public Object refreshToken(String username, String refreshToken) {
        return null;
    }
}
