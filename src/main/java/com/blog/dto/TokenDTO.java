package com.blog.dto;

public record TokenDTO(
    String accessToken,
    Long accessTokenExpireTime,
    String refreshToken,
    Long refreshTokenExpireTime) {}
