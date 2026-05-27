package ru.burunsuzian.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtClientInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> AUTH_HEADER =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers){
                String tokenValue = extractTokenVal();
                if (tokenValue != null){
                    headers.put(AUTH_HEADER, BEARER_PREFIX + tokenValue);
                } else{
                    log.debug("grpc call without jwt");
                }
                super.start(responseListener, headers);
            }
        };
    }

    private String extractTokenVal(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth){
            Jwt jwt = jwtAuth.getToken();
            return jwt != null ? jwt.getTokenValue() : null;
        }
        return null;
    }
}
