package ru.burunsuzian.grpc;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import ru.burunsuzian.config.KeycloakJwtAuthenticationConverter;

@Slf4j
@GrpcGlobalServerInterceptor
@RequiredArgsConstructor
public class JwtServerInterceptor implements ServerInterceptor {
    private static final Metadata.Key<String> AUTH_HEADER =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    private final JwtDecoder jwtDecoder;
    private final KeycloakJwtAuthenticationConverter jwtConverter;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String authHeader = headers.get(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            log.warn(call.getMethodDescriptor().getFullMethodName() + " invoked, gRPC call reject, no header or no bearer in it");
            call.close(Status.UNAUTHENTICATED.withDescription("no bearer token"), new Metadata());

            return new ServerCall.Listener<>() {};
        }

        String token = authHeader.substring("Bearer ".length());

        AbstractAuthenticationToken auth;
        try{
            Jwt jwt = jwtDecoder.decode(token);
            auth = jwtConverter.convert(jwt);
        } catch (JwtException e){
            log.warn("grpc call reject: invalid jwt, method={}, reason={}",
                    call.getMethodDescriptor().getFullMethodName(), e.getMessage());

            call.close(Status.UNAUTHENTICATED.withDescription("invalid jwt"), new Metadata());
            return new ServerCall.Listener<>(){};
        }

        AbstractAuthenticationToken finalAuth = auth;
        ServerCall.Listener<ReqT> delegate = Contexts.interceptCall(Context.current(), call, headers, next);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(delegate){
            @Override
            public void onHalfClose(){
                SecurityContextHolder.getContext().setAuthentication(finalAuth);
                try{
                    super.onHalfClose();
                } finally {
                    SecurityContextHolder.clearContext();
                }
            }
        };
    }
}
