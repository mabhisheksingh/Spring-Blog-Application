package com.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerOpenAPI {

  // @Value("${bezkoder.openapi.dev-url}")
  private String devUrl = "http://localhost:9001";

  // @Value("${bezkoder.openapi.prod-url}")
  private String prodUrl = "test";
  /**
   * OpenAPI Configuration
   *
   * @return
   */
  @Bean
  public OpenAPI myOpenAPI() {

    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("bezkoder@gmail.com");
    contact.setName("BezKoder");
    contact.setUrl("https://www.abhishek.com");

    License mitLicense =
        new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info =
        new Info()
            .title("Blog Application")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage blog related work.")
            .termsOfService("https://www.bezkoder.com/terms")
            .license(mitLicense);

    // https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#securitySchemeObject
    var securityScheme =
        new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .name("Auth Token")
            .scheme("bearer")
            .bearerFormat("opaque")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");
    var securityComponent = new Components().addSecuritySchemes("bearer", securityScheme);
    var securityItem = new SecurityRequirement().addList("bearer");
    return new OpenAPI().info(info).components(securityComponent).addSecurityItem(securityItem);
  }
}
