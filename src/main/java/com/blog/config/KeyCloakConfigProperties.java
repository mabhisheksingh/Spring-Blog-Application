package com.blog.config;

import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Data
@NoArgsConstructor
public class KeyCloakConfigProperties {

  private Optional<String> serverUrl;
  private String realm = "master";
  private Optional<String> clientId;
  private Optional<String> clientSecret;
  /**
   * Default value is client_credentials. When using password grant type, username and password are
   * required.
   */
  private String grantType = OAuth2Constants.CLIENT_CREDENTIALS;

  private String username = "admin";
  private String password = "admin";
}
