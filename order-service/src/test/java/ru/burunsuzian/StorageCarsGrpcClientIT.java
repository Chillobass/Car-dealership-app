package ru.burunsuzian;

import io.grpc.Status;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.burunsuzian.application.CarGrpcDto;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.exceptions.StorageServiceUnavailableException;
import ru.burunsuzian.grpc.StorageCarsGrpcClient;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StorageCarsGrpcClientIT extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageCarsGrpcClient storageCarsGrpcClient;

    private static RequestPostProcessor userJwt(){
        return jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    @DisplayName("GET /api/v1/cars")
    public void shouldListCars() throws Exception {
        when(storageCarsGrpcClient.listAvailableCars()).thenReturn(List.of(
                new CarGrpcDto(UUID.randomUUID().toString(), null,
                        "BMW", "BLACK", "50000", false, true)));

        mockMvc.perform(get("/api/v1/cars").with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("BMW"));
    }

    @Test @DisplayName("GET /api/v1/cars")
    public void shouldReturnEmptyListWhenNothingAvailable() throws Exception {
        when(storageCarsGrpcClient.listAvailableCars()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/cars").with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test @DisplayName("GET /api/v1/cars{id} returns car")
    public void shouldGetSingleCar() throws Exception{
        UUID id = UUID.randomUUID();
        when(storageCarsGrpcClient.listAvailableCars()).thenReturn(
                List.of(new CarGrpcDto(id.toString(), null,
                        "Tesla", "BLACK", "50000", false, true)
                ));

        mockMvc.perform(get("/api/v1/cars" + id).with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Tesla"));
    }

    @Test @DisplayName("503, если storage недоступен")
    public void shouldReturn503WhenStorageUnavailable() throws Exception{
        when(storageCarsGrpcClient.listAvailableCars())
                .thenThrow(new StorageServiceUnavailableException("storage is unavailable"));

        mockMvc.perform(get("/api/v1/cars").with(userJwt()))
                .andExpect(status().isServiceUnavailable());
    }

    @Test @DisplayName("503, если storage не ответил вовремя")
    public void shouldReturn503WhenStorageTimeout() throws Exception{
        when(storageCarsGrpcClient.listAvailableCars())
                .thenThrow(new StorageServiceUnavailableException("storage did not respond in time"));

        mockMvc.perform(get("/api/v1/cars").with(userJwt()))
                .andExpect(status().isServiceUnavailable());
    }

    @Test @DisplayName("404, если машина не найдена")
    public void shouldReturn404WhenCarNotFound() throws Exception{
        when(storageCarsGrpcClient.getAvailableCar(any()))
                .thenThrow(new EntityNotFoundException("car not found"));

        mockMvc.perform(get("/api/v1/cars" + UUID.randomUUID()).with(userJwt()))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("412, если машина не доступна")
    public void shouldReturn412WhenCarNotAvailable() throws Exception{
        when(storageCarsGrpcClient.getAvailableCar(any()))
                .thenThrow(new EntityNotFoundException("car is sold or on a testDrive"));

        mockMvc.perform(get("/api/v1/cars" + UUID.randomUUID()).with(userJwt()))
                .andExpect(status().isPreconditionFailed());
    }

    @Test @DisplayName("403, недостаточно прав")
    public void shouldReturn403ForWarehouseAdmin() throws Exception{
        mockMvc.perform(get("/api/v1/cars" + UUID.randomUUID()).with(jwt().authorities(
                new SimpleGrantedAuthority("ROLE_WAREHOUSE_ADMIN"))))
                .andExpect(status().isForbidden());
    }

    @Test @DisplayName("401, нет jwt")
    public void shouldReturn401WithoutAuth() throws Exception{
        mockMvc.perform(get("/api/v1/cars")).andExpect(status().isUnauthorized());
    }
}
