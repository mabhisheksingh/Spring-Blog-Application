package com.blog.security.idp;

public interface BasicAuthFeature {

    Object login(String username, String password);
    Boolean logout(String username);
    Object refreshToken(String username, String refreshToken);

} 
