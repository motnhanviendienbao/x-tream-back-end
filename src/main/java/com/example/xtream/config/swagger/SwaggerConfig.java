package com.example.xtream.config.swagger;

import com.example.xtream.constant.Swagger;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config for swagger ui mode
 */
@Configuration
public class SwaggerConfig {

    /**
     * config swagger security schema
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().
                        addSecuritySchemes(Swagger.SECURITY_SCHEMA, new SecurityScheme().
                                type(SecurityScheme.Type.HTTP).scheme(Swagger.TYPE_SCHEMA)))
                .addSecurityItem(new SecurityRequirement().addList(Swagger.SECURITY_SCHEMA));
    }

    /**
     * Group investors Apis on swagger
     */
    @Bean
    public GroupedOpenApi investorApis() {
        return GroupedOpenApi.builder()
                .group(Swagger.INVESTORS_GROUP)
                .pathsToMatch(Swagger.INVESTORS_PATH)
                .build();
    }

    /**
     * Group auth Apis on swagger
     */
    @Bean
    public GroupedOpenApi authApis() {
        return GroupedOpenApi.builder()
                .group(Swagger.AUTH_GROUP)
                .pathsToMatch(Swagger.AUTH_PATH)
                .build();
    }
}
