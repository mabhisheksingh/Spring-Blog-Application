package com.blog.externalapicall;

import com.blog.config.KeyCloakConfigProperties;
import com.blog.dto.KeyCloakResponseDTO;
import com.blog.exception.BlogException;
import com.blog.utils.constants.KeyCloakConstant;
import java.io.File;
import java.util.Collections;
import org.jboss.logging.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
/** This class is used to configure the keycloak rest client */
public class KeyCloakRestClient {

  private final Logger logger = Logger.getLogger(KeyCloakRestClient.class);
  private KeyCloakConfigProperties keyCloakConfigProperties;
  private String baseUrl;
  private final RestTemplate restTemplate;

  public KeyCloakRestClient(
      KeyCloakConfigProperties keyCloakConfigProperties, RestTemplateBuilder builder) {
    this.keyCloakConfigProperties = keyCloakConfigProperties;
    logger.info("KeyCloakRestClient is configured");
    baseUrl =
        keyCloakConfigProperties
            .getServerUrl()
            .orElseThrow(
                () -> new BlogException("adminKeyCloak.server-url missing in properties file"));
    baseUrl =
        baseUrl + KeyCloakConstant.REALMS + File.separator + keyCloakConfigProperties.getRealm();
    this.restTemplate = builder.build();
  }
  // validate token

  public Boolean isValidAccessToken(String accessToken) {
    logger.info("isValidAccessToken is called");
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    // Prepare URL-encoded form data
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("token", accessToken); // This will be URL-encoded
    body.add("client_id", keyCloakConfigProperties.getClientId().get());
    body.add("client_secret", keyCloakConfigProperties.getClientSecret().get());

    // Create the HttpEntity with headers and body
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
    logger.info("REQUEST--> " + requestEntity);

    logger.info("base URL " + baseUrl + KeyCloakConstant.VALIDATE_TOKEN_URL);
    KeyCloakResponseDTO keyCloakResponseDTO =
        restTemplate
            .exchange(
                baseUrl + KeyCloakConstant.VALIDATE_TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                KeyCloakResponseDTO.class)
            .getBody();

    logger.info("KeyCloakResponseDTO Output--> " + keyCloakResponseDTO);
    assert keyCloakResponseDTO != null;
    return keyCloakResponseDTO.isActive() ? Boolean.TRUE : Boolean.FALSE;
  }
}
