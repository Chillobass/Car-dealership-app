package ru.burunsuzian;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import ru.burunsuzian.application.dto.CarOrderDto;
import ru.burunsuzian.config.RabbitMqConfig;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.data.persistence.repositories.WarehouseAcceptanceRepository;
import ru.burunsuzian.application.events.WarehouseApprovalRequest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class StorageBrokerIT extends BaseIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WarehouseAcceptanceRepository warehouseRepository;

    @Autowired
    private CarsRepository carsRepository;

    @MockBean
    private RestTemplate restTemplate;

    @AfterEach
    void cleanUp() {
        warehouseRepository.deleteAll();
        carsRepository.deleteAll();
    }

    private void setupMockDataForOrder(UUID carOrderId) {
        UUID carId = UUID.randomUUID();
        UUID managerId = UUID.randomUUID();

        CarEntity car = new CarEntity();
        car.setId(carId);
        carsRepository.save(car);

        CarOrderDto mockOrderResponse = new CarOrderDto(carId, managerId);
        when(restTemplate.getForObject(contains(carOrderId.toString()), eq(CarOrderDto.class)))
                .thenReturn(mockOrderResponse);
    }

    @Test
    void shouldProcessWarehouseApprovalRequestFromRabbitMQ() {
        // Arrange
        UUID carOrderId = UUID.randomUUID();
        setupMockDataForOrder(carOrderId);

        WarehouseApprovalRequest request = new WarehouseApprovalRequest(carOrderId, "traceId_idk", true);

        // Act
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request);

        // Assert
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    boolean isCreatedInDb = !warehouseRepository.findAllByCarOrderId(carOrderId).isEmpty();
                    assertThat(isCreatedInDb).isTrue();
                });
    }

    @Test
    void shouldCreateWarehouseAcceptanceWithCorrectInitialState() {
        // Arrange
        UUID carOrderId = UUID.randomUUID();
        setupMockDataForOrder(carOrderId);

        WarehouseApprovalRequest request = new WarehouseApprovalRequest(carOrderId, "traceId_idk", false);

        // Act
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request);

        // Assert
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    var acceptances = warehouseRepository.findAllByCarOrderId(carOrderId);

                    assertThat(acceptances).hasSize(1);
                    assertThat(acceptances.iterator().next().getState()).isEqualTo("CREATED");
                });
    }

    @Test
    void shouldBeIdempotentAndIgnoreDuplicateRequests() throws InterruptedException {
        // Arrange
        UUID carOrderId = UUID.randomUUID();
        setupMockDataForOrder(carOrderId);

        WarehouseApprovalRequest request = new WarehouseApprovalRequest(carOrderId, "traceId_idk", true);

        // Act
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request);

        // Assert
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    assertThat(warehouseRepository.findAllByCarOrderId(carOrderId)).isNotEmpty();
                });

        Thread.sleep(500);

        assertThat(warehouseRepository.findAllByCarOrderId(carOrderId)).hasSize(1);
    }

    @Test
    void shouldProcessMultipleDistinctRequestsCorrectly() {
        // Arrange
        UUID carOrderId1 = UUID.randomUUID();
        UUID carOrderId2 = UUID.randomUUID();

        setupMockDataForOrder(carOrderId1);
        setupMockDataForOrder(carOrderId2);

        WarehouseApprovalRequest request1 = new WarehouseApprovalRequest(carOrderId1, "traceId_idk", true);
        WarehouseApprovalRequest request2 = new WarehouseApprovalRequest(carOrderId2, "another_traceId_idk", false);

        // Act
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request1);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", request2);

        // Assert
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    boolean isFirstCreated = !warehouseRepository.findAllByCarOrderId(carOrderId1).isEmpty();
                    boolean isSecondCreated = !warehouseRepository.findAllByCarOrderId(carOrderId2).isEmpty();

                    assertThat(isFirstCreated).isTrue();
                    assertThat(isSecondCreated).isTrue();
                });
    }
}