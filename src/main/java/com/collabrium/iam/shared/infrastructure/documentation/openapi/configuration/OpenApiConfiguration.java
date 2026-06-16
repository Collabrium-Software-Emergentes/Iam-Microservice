package com.collabrium.iam.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  private static final String SECURITY_SCHEME_NAME = "bearerAuth";

  @Bean
  public OpenAPI iamPlatformOpenAPI() {

    return new OpenAPI()
        .info(apiInfo())
        .externalDocs(externalDocs())
        .addSecurityItem(securityRequirement())
        .components(securityComponents());
  }

  private Info apiInfo() {

    return new Info()
        .title("Iam Platform API")
        .description("Iam Platform application REST API documentation.")
        .version("v1.0.0")
        .license(new License()
            .name("Apache 2.0")
            .url("https://springdoc.org"));
  }

  private ExternalDocumentation externalDocs() {

    return new ExternalDocumentation()
        .description("Iam Platform Documentation")
        .url("https://github.com/Collabrium-Software-Emergentes/Synhub-Microservices-Old.git");
  }

  private SecurityRequirement securityRequirement() {

    return new SecurityRequirement()
        .addList(SECURITY_SCHEME_NAME);
  }

  private Components securityComponents() {

    return new Components()
        .addSecuritySchemes(
            SECURITY_SCHEME_NAME,
            bearerSecurityScheme()
        );
  }

  private SecurityScheme bearerSecurityScheme() {

    return new SecurityScheme()
        .name(SECURITY_SCHEME_NAME)
        .description("JWT Authorization header using Bearer scheme.")
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
  }
}
