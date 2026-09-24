package ru.burunsuzian.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@OpenAPIDefinition(
        info = @Info(title = "car dealership api", version = "v1"),
        security = @SecurityRequirement(name = "keycloak_auth")
)
@SecurityScheme(
        name = "keycloak_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:8081/realms/car-realm/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:8081/realms/car-realm/protocol/openid-connect/token",
                        scopes = {
                                @OAuthScope(name = "openid"),
                                @OAuthScope(name = "profile")
                        }
                )
        )
)
@RequiredArgsConstructor
public class SecurityConfig {
    private final KeycloakJwtAuthenticationConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/v1/health").permitAll()
                            .requestMatchers("/details/**").hasAnyRole("ADMIN", "WAREHOUSE_ADMIN")
            .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
            ).permitAll()
                        .anyRequest().hasAnyRole("ADMIN", "WAREHOUSE_ADMIN", "USER", "MANAGER")
            ).oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
        );
        return http.build();
    }
}