package ru.burunsuzian.grpc;

import java.nio.file.AccessDeniedException;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.naming.AuthenticationException;

@Slf4j
@GrpcAdvice
public class GrpcExceptionMapper {

    @GrpcExceptionHandler(AccessDeniedException.class)
    public Status handleAccessDenied(AccessDeniedException e){
        log.warn("grpc access denied: {}", e.getMessage());
        return Status.PERMISSION_DENIED.withDescription("u don't have enough rights");
    }

    @GrpcExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public Status handleNoCredentials(AuthenticationCredentialsNotFoundException e){
        log.warn("no credentials in security context: {}", e.getMessage());
        return Status.UNAUTHENTICATED.withDescription("auth required");
    }

    @GrpcExceptionHandler(AuthenticationException.class)
    public Status handleAuthFailure(AuthenticationException e){
        log.warn("grpc auth failure: {}", e.getMessage());
        return Status.UNAUTHENTICATED.withDescription(e.getMessage());
    }

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public Status handleIllegalArgument(IllegalArgumentException e){
        log.warn("grpc invalid argument: {}", e.getMessage());
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage());
    }
}
