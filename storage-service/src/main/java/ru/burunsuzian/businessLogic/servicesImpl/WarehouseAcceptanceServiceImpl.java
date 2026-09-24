package ru.burunsuzian.businessLogic.servicesImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.burunsuzian.application.dto.CarOrderDto;
import ru.burunsuzian.application.events.WarehouseApprovalResponse;
import ru.burunsuzian.application.responses.WarehouseAcceptanceResponse;
import ru.burunsuzian.businessLogic.domain.WarehouseAcceptance;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.mappers.WarehouseAcceptanceMapper;
import ru.burunsuzian.data.jpa.OutboxMessageEntity;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.jpa.domainEntities.WarehouseAcceptanceEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;
import ru.burunsuzian.data.persistence.repositories.WarehouseAcceptanceRepository;
import ru.burunsuzian.data.persistence.services.WarehouseAcceptanceService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseAcceptanceServiceImpl implements WarehouseAcceptanceService {

    private WarehouseAcceptanceRepository warehouseAcceptanceRepository;
    private RestTemplate restTemplate;
    private CarsRepository carsRepository;
    private ObjectMapper objectMapper;
    private OutboxMessageRepository outboxMessageRepository;
    private WarehouseAcceptanceMapper warehouseAcceptanceMapper;
    private Tracer tracer;

    @Override
    public UUID createWarehouseAcceptance(UUID carOrderId, boolean isCustomCar) {
        CarOrderDto order = restTemplate.getForObject("http://order-service:8081/carOrders/get/" + carOrderId, CarOrderDto.class);
        if (order == null) throw new EntityNotFoundException("Order not found");

        CarEntity car = carsRepository.findById(order.carId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Set<ReplaceableComponentType> components = new HashSet<>();
        if (car.getSteeringWheel() != null) components.add(car.getSteeringWheel());
        if (car.getDecor() != null) components.add(car.getDecor());
        if (car.getTransmission() != null) components.add(car.getTransmission());
        if (car.getWheels() != null) components.add(car.getWheels());

        WarehouseAcceptanceEntity entity = WarehouseAcceptanceEntity.builder()
                .id(UUID.randomUUID())
                .carOrderId(carOrderId)
                .orderType(isCustomCar ? "CUSTOM" : "REGULAR")
                .carId(car.getId())
                .carModelId(car.getModel() != null ? car.getModel().getId() : null)
                .requiredComponents(components)
                .responsibleStaffId(order.managerId())
                .state("CREATED")
                .build();

        warehouseAcceptanceRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseAcceptanceResponse> getWarehouseAcceptance(UUID carOrderId) {
        return warehouseAcceptanceRepository.findAllByCarOrderId(carOrderId).stream()
                .map(warehouseAcceptanceMapper::toDomain)
                .map(warehouseAcceptanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseAcceptanceResponse> getAllWarehouseAcceptances() {
        return warehouseAcceptanceRepository.findAll().stream()
                .map(warehouseAcceptanceMapper::toDomain)
                .map(warehouseAcceptanceMapper::toResponse)
                .toList();
    }

    private WarehouseAcceptanceResponse mapToResponse(WarehouseAcceptanceEntity entity) {
        Set<UUID> componentIds = entity.getRequiredComponents() == null ? new HashSet<>() :
                entity.getRequiredComponents().stream()
                        .map(ReplaceableComponentType::getId)
                        .collect(Collectors.toSet());

        return new WarehouseAcceptanceResponse(
                entity.getId(), entity.getCarOrderId(), entity.getOrderType(),
                entity.getCarId(), entity.getCarModelId(), componentIds,
                entity.getResponsibleStaffId(), entity.getState()
        );
    }

    @Override
    @Transactional
    public String updateWarehouseAcceptance(UUID warehouseAcceptanceId) throws JsonProcessingException {
        WarehouseAcceptanceEntity entity = warehouseAcceptanceRepository.getReferenceById(warehouseAcceptanceId);

        WarehouseAcceptance domain = warehouseAcceptanceMapper.toDomain(entity);

        if ("ASSEMBLED".equals(entity.getState()) || "FAIL".equals(entity.getState())) {
            throw new IllegalStateException("Сборка уже завершена");
        }

        boolean isApproved = isComponentReservedByCar(entity.getCarId());
        domain.processAssembly(isApproved);

        warehouseAcceptanceMapper.updateEntity(domain, entity);
        warehouseAcceptanceRepository.save(entity);

        String currentTraceId = MDC.get("traceId");
        String currentSpanId = null;

        WarehouseApprovalResponse response = new WarehouseApprovalResponse(domain.getCarOrderId(), "some_trace", isApproved);
        OutboxMessageEntity outbox = OutboxMessageEntity.builder()
                .eventType("WAREHOUSE_APPROVAL_RESPONSE")
                .stat("CREATED")
                .carOrderId(domain.getCarOrderId())
                .traceId(currentTraceId)
                .spanId(currentSpanId)
                .payload(objectMapper.writeValueAsString(response))
                .build();
        outboxMessageRepository.save(outbox);

        return domain.getState();
    }

    private boolean isComponentReservedByCar(UUID carId) {
        if (carId == null) return false;
        CarEntity car = carsRepository.getReferenceById(carId);
        return (car.getDecor() != null && Objects.equals(car.getDecor().getReservedBy(), carId)) ||
                (car.getSteeringWheel() != null && Objects.equals(car.getSteeringWheel().getReservedBy(), carId)) ||
                (car.getWheels() != null && Objects.equals(car.getWheels().getReservedBy(), carId)) ||
                (car.getTransmission() != null && Objects.equals(car.getTransmission().getReservedBy(), carId));
    }

    @Override
    @Transactional
    public void deleteWarehouseAcceptance(UUID warehouseAcceptanceId) {
        warehouseAcceptanceRepository.deleteById(warehouseAcceptanceId);
    }

}
