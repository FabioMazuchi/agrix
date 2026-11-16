package com.betrybe.agrix.ebytr.staff.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * The type Config swagger.
 */
@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Informe o token JWT recebido no login"
)
public class ConfigSwagger {
}
