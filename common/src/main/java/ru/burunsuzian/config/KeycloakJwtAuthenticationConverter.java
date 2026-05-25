package ru.burunsuzian.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private List<GrantedAuthority> extractResourceRoles(Jwt source){
        Map<String, Object> realmAccess = source.getClaim("realm_access");
        if (realmAccess == null || !realmAccess.containsKey("roles")){
            return List.of();
        }
        List<String> roles = (List<String>)(realmAccess.get("roles"));

        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        List<GrantedAuthority> authorities = extractResourceRoles(source);
        return new JwtAuthenticationToken(source, authorities, source.getClaim("preferred_username"));
    }
}
