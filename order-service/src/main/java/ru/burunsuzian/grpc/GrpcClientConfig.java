package ru.burunsuzian.grpc;


import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GrpcClientConfig {
    @GrpcGlobalClientInterceptor
    public JwtClientInterceptor jwtClientInterceptor(){
        return new JwtClientInterceptor();
    }
}
