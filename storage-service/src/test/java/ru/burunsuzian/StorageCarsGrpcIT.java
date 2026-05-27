package ru.burunsuzian;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.jpa.domainEntities.CarModelEntity;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.grpc.storage.v1.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class StorageCarsGrpcIT extends BaseIntegrationTest{
    private static final String USER_TOKEN = "test-token-user";
    private static final String ADMIN_TOKEN = "test-token-admin";
    private static final String INVALID_TOKEN = "test-token-invalid";
    private static final String NO_ROLES_TOKEN = "test-token-noroles";

    private static final Metadata.Key<String> AUTH_HEADER = Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private CarsRepository carsRepository;

    private int grpcPort;

    private ManagedChannel channel;

    private static Jwt buildJwt(String tokenValue, List<String> roles){
        return Jwt.withTokenValue(tokenValue)
                .header("alg", "none")
                .claim("realm_access", Map.of("roles", roles))
                .claim("sub", UUID.randomUUID().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600)).build();
    }

    @BeforeEach
    void setup(){
        when(jwtDecoder.decode(eq(USER_TOKEN))).thenReturn(buildJwt(USER_TOKEN, List.of("USER")));
        when(jwtDecoder.decode(eq(ADMIN_TOKEN))).thenReturn(buildJwt(ADMIN_TOKEN, List.of("ADMIN")));
        when(jwtDecoder.decode(eq(NO_ROLES_TOKEN))).thenReturn(buildJwt(NO_ROLES_TOKEN, List.of("WAREHOUSE_ADMIN")));
        when(jwtDecoder.decode(eq(INVALID_TOKEN))).thenThrow(new JwtException("bad token"));

        carsRepository.deleteAll();

        channel = ManagedChannelBuilder.forAddress("localhost", grpcPort)
                .usePlaintext().build();
    }

    @AfterEach
    void tearDown(){
        if (channel != null){
            channel.shutdownNow();
        }
    }

    private StorageCarsServiceGrpc.StorageCarsServiceBlockingStub stub(String token){
        Metadata md = new Metadata();
        if (token != null){
            md.put(AUTH_HEADER, "Bearer " + token);
        }
        return StorageCarsServiceGrpc.newBlockingStub(channel).withInterceptors(MetadataUtils.newAttachHeadersInterceptor(md));
    }

    private CarEntity saveCar(boolean available){
        CarModelEntity model = CarModelEntity.builder()
                .brand("TestBrand")
                .basePrice(new BigDecimal("100000.0"))
                .carColor("BLACK")
                .build();

        CarEntity entity = CarEntity.builder()
                .model(model)
                .isAvailable(available)
                .build();

        carsRepository.save(entity);
        return entity;
    }

    @Test
    public void shouldReturnOnlyAvailableCars(){
        CarEntity available = saveCar(true);
        saveCar(false);

        ListAvailableCarsResponse response = stub(USER_TOKEN).listAvailableCars(ListAvailableCarsRequest.newBuilder().build());

        assertThat(response.getCarsList())
                .extracting(CarProto::getCarId)
                .containsExactly(available.getId().toString());
    }

    @Test
    public void shouldReturnEmptyListWhenNothingAvailable(){
        saveCar(false);
        ListAvailableCarsResponse response = stub(USER_TOKEN).listAvailableCars(
                ListAvailableCarsRequest.newBuilder().build());

        assertThat(response.getCarsList()).isEmpty();
    }

    @Test
    public void shouldReturnByCarId(){
        CarEntity car = saveCar(true);

        GetAvailableCarResponse response = stub(USER_TOKEN)
                .getAvailableCar(GetAvailableCarRequest.newBuilder().setCarId(car.getId().toString()).build());

        assertThat(response.getCar().getCarId()).isEqualTo(car.getId().toString());
    }
}
