package com.example.xtream.config.swagger;

import com.example.xtream.constant.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

/**
 * config for swagger ui mode
 */
@org.springframework.context.annotation.Configuration
public class SwaggerConfig {

    /**
     * config swagger security schema
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().
                        addSecuritySchemes(Configuration.SWAGGER_SECURITY_SCHEMA, new SecurityScheme().
                                type(SecurityScheme.Type.HTTP).scheme(Configuration.SWAGGER_TYPE_SCHEMA)))
                .addSecurityItem(new SecurityRequirement().addList(Configuration.SWAGGER_SECURITY_SCHEMA));
    }

    /**
     * Group investors Apis on swagger
     */
    @Bean
    public GroupedOpenApi investorApis() {
        return GroupedOpenApi.builder()
                .group(Configuration.SWAGGER_INVESTORS_GROUP)
                .pathsToMatch(Configuration.SWAGGER_INVESTORS_PATH)
                .build();
    }

    /**
     * Group auth Apis on swagger
     */
    @Bean
    public GroupedOpenApi authApis() {
        return GroupedOpenApi.builder()
                .group(Configuration.SWAGGER_AUTH_GROUP)
                .pathsToMatch(Configuration.SWAGGER_AUTH_PATH)
                .build();
    }
}
