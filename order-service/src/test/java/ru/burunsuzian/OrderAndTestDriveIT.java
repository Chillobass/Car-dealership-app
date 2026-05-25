package ru.burunsuzian;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import ru.burunsuzian.application.StorageCarDto;
import ru.burunsuzian.data.persistence.repositories.CarOrderRepository;
import ru.burunsuzian.data.persistence.repositories.TestDriveRequestRepository;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderAndTestDriveIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarOrderRepository carOrderRepository;

    @Autowired
    private TestDriveRequestRepository testDriveRepository;

    @MockBean
    private RestTemplate restTemplate;

    @AfterEach
    void cleanUp() {
        carOrderRepository.deleteAll();
        testDriveRepository.deleteAll();
    }

    @Test
    void shouldCreateCarOrderSuccessfully() throws Exception {
        UUID carId = UUID.randomUUID();
        UUID managerId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        StorageCarDto mockStorageCar = new StorageCarDto(carId, true);
        when(restTemplate.getForObject(anyString(), eq(StorageCarDto.class))).thenReturn(mockStorageCar);

        String jsonRequest = """
                {
                  "managerId": "%s",
                  "carId": "%s",
                  "state": "PLACED"
                }
                """.formatted(managerId, carId);

        mockMvc.perform(post("/carOrders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .with(jwt().jwt(j -> j.subject(customerId.toString()))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customerId.toString()))
                .andExpect(jsonPath("$.carId").value(carId.toString()));

        assertThat(carOrderRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldCreateTestDriveRequestSuccessfully() throws Exception {
        UUID carId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Instant time = Instant.now().plusSeconds(3600);

        when(restTemplate.getForObject(anyString(), eq(UUID.class))).thenReturn(carId);

        String jsonRequest = """
                {
                  "customerId": "%s",
                  "carId": "%s",
                  "time": "%s"
                }
                """.formatted(customerId, carId, time);

        mockMvc.perform(post("/testDrive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carId").value(carId.toString()));

        assertThat(testDriveRepository.findAll()).hasSize(1);
    }
}