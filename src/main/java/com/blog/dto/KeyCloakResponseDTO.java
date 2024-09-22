package com.blog.dto;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class KeyCloakResponseDTO {
  private long exp;
  private long iat;
  private String jti;
  private String iss;
  private String aud;
  private String sub;
  private String typ;
  private String azp;
  private String sid;
  private String acr;
  private List<String> allowedOrigins;
  private RealmAccess realmAccess;
  private ResourceAccess resourceAccess;
  private String scope;
  private boolean emailVerified;
  private String name;
  private String preferredUsername;
  private String givenName;
  private String familyName;
  private String email;
  private String clientId;
  private String username;
  private String tokenType;
  private boolean active;

  @Data
  public static class RealmAccess {
    private List<String> roles;
  }

  @Data
  public static class ResourceAccess {
    private Map<String, Resource> account;
  }

  @Data
  public static class Resource {
    private List<String> roles;
  }
}
