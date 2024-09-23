package com.blog.external_api_call;

import com.blog.config.KeyCloakConfigProperties;
import com.blog.exception.BlogException;
import com.blog.utils.constants.KeyCloakConstant;
import java.io.File;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientFactory {

  private final KeyCloakConfigProperties keyCloakConfigProperties;

  private String baseUrl;

  public HttpClientFactory(KeyCloakConfigProperties keyCloakConfigProperties) {
    this.keyCloakConfigProperties = keyCloakConfigProperties;
  }

  @Bean
  public KeyCloakRestHttpClient restClient(RestTemplateBuilder restTemplateBuilder) {
    baseUrl =
        keyCloakConfigProperties
            .getServerUrl()
            .orElseThrow(
                () -> new BlogException("adminKeyCloak.server-url missing in properties file"));
    baseUrl =
        baseUrl + KeyCloakConstant.REALMS + File.separator + keyCloakConfigProperties.getRealm();

    SimpleClientHttpRequestFactory clientHttpRequestFactorySettings =
        new SimpleClientHttpRequestFactory();
    clientHttpRequestFactorySettings.setConnectTimeout(5000);
    clientHttpRequestFactorySettings.setReadTimeout(5000);

    RestClient client =
        RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .requestFactory(clientHttpRequestFactorySettings)
            .build();

    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client)).build();

    return factory.createClient(KeyCloakRestHttpClient.class);
  }
}
