package ru.burunsuzian.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl("http://keycloak:8080")
                .realm("${KEYCLOAK_REALM}")
                .username("${KEYCLOAK_USERNAME}")
                .password("${KEYCLOAK_PASSWORD}")
                .clientId("${KEYCLOAK_CLIENT}")
                .build();
    }
}
