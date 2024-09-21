package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDTO {
    private String accessToken;
    private Long accessTokenExpireTime;
    private String refreshToken;
    private Long refreshTokenExpireTime;
}
