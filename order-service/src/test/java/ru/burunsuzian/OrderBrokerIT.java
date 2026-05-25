package ru.burunsuzian;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import ru.burunsuzian.application.events.WarehouseApprovalResponse;
import ru.burunsuzian.config.WarehouseAcceptanceResponseListener;
import ru.burunsuzian.data.jpa.CarOrderEntity;
import ru.burunsuzian.data.jpa.CustomOrderStateEntity;
import ru.burunsuzian.data.persistence.repositories.CarOrderRepository;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;
import ru.burunsuzian.businessLogic.servicesImpl.CarOrderServiceImpl;
import jakarta.persistence.EntityManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class OrderBrokerIT extends BaseIntegrationTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CarOrderRepository carOrderRepository;

    @Autowired
    private OutboxMessageRepository outboxRepository;

    @Autowired
    private CarOrderServiceImpl carOrderService;

    @Autowired
    private WarehouseAcceptanceResponseListener listener;

    @AfterEach
    void cleanUp() {
        carOrderRepository.deleteAll();
        outboxRepository.deleteAll();
    }

    @Test
    void shouldCreateOutboxMessageWhenCustomCarTransitionsToWarehouseApproved() throws Exception {
        // Arrange
        CarOrderEntity order = new CarOrderEntity();
        order.setCustomerId(UUID.randomUUID());
        order.setManagerId(UUID.randomUUID());
        order.setCarId(UUID.randomUUID());
        order.setCustomCar(true);
        order.setOrderState(new CustomOrderStateEntity("PLACED"));
        order = carOrderRepository.save(order);

        // Act
        carOrderService.transitionTo(order.getId(), "WAREHOUSE_APPROVED");

        // Assert
        assertThat(outboxRepository.findAll()).hasSize(1);
        assertThat(outboxRepository.findAll().get(0).getEventType()).isEqualTo("WarehouseAcceptanceRequest");
    }

    @Test
    void shouldUpdateOrderStateToWarehouseApprovedWhenWarehouseApproves() throws Exception {
        // Arrange
        CarOrderEntity order = new CarOrderEntity();
        order.setCustomerId(UUID.randomUUID());
        order.setManagerId(UUID.randomUUID());
        order.setCarId(UUID.randomUUID());
        order.setCustomCar(true);
        order.setOrderState(new CustomOrderStateEntity("PLACED"));
        order = carOrderRepository.save(order);

        WarehouseApprovalResponse mockResponse = new WarehouseApprovalResponse(
                order.getId(), "randomTrace", true
        );

        // Act
        listener.handleWarehouseResponse(mockResponse);

        entityManager.flush();
        entityManager.clear();

        // Assert
        CarOrderEntity updatedOrder = carOrderRepository.findById(order.getId()).orElseThrow();
        assertThat(updatedOrder.getOrderState().getState()).isEqualTo("WAREHOUSE_APPROVED");
    }

    @Test
    void shouldUpdateOrderStateToCancelledWhenWarehouseDenies() throws Exception {
        // Arrange
        CarOrderEntity order = new CarOrderEntity();
        order.setCustomerId(UUID.randomUUID());
        order.setCustomCar(true);
        order.setOrderState(new CustomOrderStateEntity("PLACED"));
        order = carOrderRepository.save(order);

        WarehouseApprovalResponse mockResponse = new WarehouseApprovalResponse(
                order.getId(), "randomTraceForever", false
        );

        // Act
        listener.handleWarehouseResponse(mockResponse);

        entityManager.flush();
        entityManager.clear();

        // Assert
        CarOrderEntity updatedOrder = carOrderRepository.findById(order.getId()).orElseThrow();
        assertThat(updatedOrder.getOrderState().getState()).isEqualTo("CANCELLED");
    }
}