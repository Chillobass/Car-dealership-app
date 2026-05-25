package ru.burunsuzian;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import ru.burunsuzian.application.dto.CarOrderDto;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.jpa.domainEntities.CarModelEntity;
import ru.burunsuzian.data.jpa.domainEntities.WarehouseAcceptanceEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.AutomaticTransmissionEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.BestDecorEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.HotWheelsEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.RoundSteeringWheelEntity;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;
import ru.burunsuzian.data.persistence.repositories.WarehouseAcceptanceRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WarehouseAcceptanceIT extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private WarehouseAcceptanceRepository warehouseRepository;
    @Autowired private OutboxMessageRepository outboxRepository;
    @Autowired private CarsRepository carsRepository;

    @MockBean private RestTemplate restTemplate;

    @AfterEach
    void cleanUp() {
        warehouseRepository.deleteAll();
        outboxRepository.deleteAll();
        carsRepository.deleteAll();
    }

    @Test
    void shouldCreateWarehouseAcceptance() throws Exception {
        UUID carOrderId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        UUID managerId = UUID.randomUUID();

        CarEntity car = new CarEntity();
        car.setId(carId);
        CarModelEntity model = new CarModelEntity();
        model.setId(UUID.randomUUID());
        car.setModel(model);
        carsRepository.save(car);

        CarOrderDto mockOrderResponse = new CarOrderDto(carId, managerId);
        when(restTemplate.getForObject(contains(carOrderId.toString()), eq(CarOrderDto.class)))
                .thenReturn(mockOrderResponse);

        // Act and Assert
        mockMvc.perform(post("/warehouse_acceptance/create/{carOrderId}", carOrderId)
                        .param("isCustomCar", "true")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_WAREHOUSE_ADMIN"))))
                .andExpect(status().isCreated());

        var acceptances = warehouseRepository.findAllByCarOrderId(carOrderId);
        assertThat(acceptances).hasSize(1);

        WarehouseAcceptanceEntity saved = acceptances.iterator().next();
        assertThat(saved.getOrderType()).isEqualTo("CUSTOM");
        assertThat(saved.getCarId()).isEqualTo(carId);
        assertThat(saved.getResponsibleStaffId()).isEqualTo(managerId);
        assertThat(saved.getState()).isEqualTo("CREATED");
    }

    @Test
    void shouldGetWarehouseAcceptance() throws Exception {
        UUID carOrderId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        UUID managerId = UUID.randomUUID();

        WarehouseAcceptanceEntity entity = WarehouseAcceptanceEntity.builder()
                .id(UUID.randomUUID())
                .carOrderId(carOrderId)
                .orderType("REGULAR")
                .carId(carId)
                .responsibleStaffId(managerId)
                .state("CREATED")
                .build();
        warehouseRepository.save(entity);

        // Act & Assert
        mockMvc.perform(get("/warehouse_acceptance/get/{carOrderId}", carOrderId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_MANAGER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(entity.getId().toString()))
                .andExpect(jsonPath("$[0].orderType").value("REGULAR"))
                .andExpect(jsonPath("$[0].carId").value(carId.toString()))
                .andExpect(jsonPath("$[0].responsibleStaffId").value(managerId.toString()))
                .andExpect(jsonPath("$[0].state").value("CREATED"));
    }

    @Test
    void shouldUpdateWarehouseAcceptanceAndSaveToOutbox() throws Exception {
        UUID carOrderId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        CarEntity car = new CarEntity();
        car.setId(carId);

        BestDecorEntity decor = new BestDecorEntity();
        decor.setId(UUID.randomUUID());
        decor.setReservedBy(carId);
        car.setDecor(decor);

        RoundSteeringWheelEntity wheel = new RoundSteeringWheelEntity();
        wheel.setId(UUID.randomUUID());
        wheel.setReservedBy(carId);
        car.setSteeringWheel(wheel);

        HotWheelsEntity wheels = new HotWheelsEntity();
        wheels.setId(UUID.randomUUID());
        wheels.setReservedBy(carId);
        car.setWheels(wheels);

        AutomaticTransmissionEntity transmission = new AutomaticTransmissionEntity();
        transmission.setId(UUID.randomUUID());
        transmission.setReservedBy(carId);
        car.setTransmission(transmission);

        carsRepository.save(car);

        WarehouseAcceptanceEntity entity = WarehouseAcceptanceEntity.builder()
                .id(UUID.randomUUID())
                .carOrderId(carOrderId)
                .orderType("CUSTOM")
                .carId(carId)
                .state("PICKING")
                .build();
        warehouseRepository.save(entity);

        // Act & Assert
        mockMvc.perform(put("/warehouse_acceptance/update/{id}", entity.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_WAREHOUSE_ADMIN"))))
                .andExpect(status().isOk());

        WarehouseAcceptanceEntity updatedEntity = warehouseRepository.findById(entity.getId()).orElseThrow();
        assertThat(updatedEntity.getState()).isEqualTo("ASSEMBLED");

        assertThat(outboxRepository.findAll()).hasSize(1);
        assertThat(outboxRepository.findAll().get(0).getEventType()).isEqualTo("WAREHOUSE_APPROVAL_RESPONSE");
    }
}