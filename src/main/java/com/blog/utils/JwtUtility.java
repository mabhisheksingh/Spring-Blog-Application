package com.blog.utils;

import com.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
/** Utility class for JWT operations. */
@Component
public final class JwtUtility {
  private JwtUtility() {}

  /**
   * Parses the given JWT token and returns the claims.
   *
   * @param token The JWT token to be parsed.
   * @return The claims extracted from the token. Examle : claims: JWT Claims Set:{exp=1727340422,
   *     iat=1727339882, jti=bd7601f6-fa90-4784-bc1a-2c3d3248bb2e,
   *     iss=http://localhost:8080/realms/blog, aud=account,
   *     sub=cbeac751-dfb0-472e-b6c6-8686e5dbb4ba, typ=Bearer, azp=blog,
   *     sid=9b669c54-fb77-4f9b-9624-30f69f92d318, acr=1, allowed-origins=[http://localhost:9001/],
   *     realm_access={roles=[offline_access, admin, uma_authorization, user, default-roles-blog]},
   *     resource_access={account={roles=[manage-account, manage-account-links, view-profile]}},
   *     scope=profile email, email_verified=true, name=myuser Admin, preferred_username=abhishek,
   *     given_name=myuser, family_name=Admin, email=abhishek.r.singh@impetus.com}
   */
  public static JwtClaims parseToken(String token) {
    log.info("Inside parseToken..");
    log.debug("token: {}", token);
    JwtConsumer consumer =
        new JwtConsumerBuilder()
            .setSkipAllValidators()
            .setDisableRequireSignature()
            .setSkipSignatureVerification()
            .build();
    JwtClaims claims = null;
    try {
      claims = consumer.processToClaims(token);
    } catch (InvalidJwtException e) {
      throw new BlogException("Invalid JWT token", HttpStatus.BAD_REQUEST.value());
    }
    log.info("claims: {}", claims);
    log.info("claims: -->{}", claims.getClaimValueAsString("preferred_username"));
    log.info("claims: -->{}", claims.getClaimValueAsString("realm_access"));
    log.info("claims: -->{}", claims.getClaimValueAsString("email"));
    return claims;
  }

  //  public static void main(String[] args) {
  //    parseToken(
  //
  // "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3azFlQkFZVXVPTi0zanlzV2syOWlyODAwYUxyNGQ1YWFMcHd1V2pRbTlrIn0.eyJleHAiOjE3MjczNDA0MjIsImlhdCI6MTcyNzMzOTg4MiwianRpIjoiYmQ3NjAxZjYtZmE5MC00Nzg0LWJjMWEtMmMzZDMyNDhiYjJlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9ibG9nIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImNiZWFjNzUxLWRmYjAtNDcyZS1iNmM2LTg2ODZlNWRiYjRiYSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImJsb2ciLCJzaWQiOiI5YjY2OWM1NC1mYjc3LTRmOWItOTYyNC0zMGY2OWY5MmQzMTgiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6OTAwMS8iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiLCJkZWZhdWx0LXJvbGVzLWJsb2ciXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Im15dXNlciBBZG1pbiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFiaGlzaGVrIiwiZ2l2ZW5fbmFtZSI6Im15dXNlciIsImZhbWlseV9uYW1lIjoiQWRtaW4iLCJlbWFpbCI6ImFiaGlzaGVrLnIuc2luZ2hAaW1wZXR1cy5jb20ifQ.U3JiBbv8BuFUb7bVelPfZGrfaiwsB2SFbov8WZvt5TP0fx9BO9YnJ5_SvND4TU5t8ttjW7S3nBOrm_Dk-UoKQR6MxAp6b5YANTh53eeETwrT_whzs_I4hV1xGBiG8AiLGxiK2dNxrQJSVmVFOiuL0U2-H_mDobbJB42j5Els45dISxhGmN0MvsZ_hAn5jxf9nSvY3RI7kt275PAbmKNtkdfWRb5n_kGAIWPM4MSyw-cKQ-HMkmqhqtWZj19MUcv2-JLneMRww7hd4IQ29v9IZ0TwtmSmBexeAJuUWoKW48ij0ai2u7RxQmgB7KnKkRHlqSrXArbb428c6emYX3mUyg");
  //  }
}
