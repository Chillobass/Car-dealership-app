package ru.burunsuzian.businessLogic.servicesImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import ru.burunsuzian.application.StorageCarDto;
import ru.burunsuzian.application.events.WarehouseApprovalRequest;
import ru.burunsuzian.application.responses.CarOrderResponse;
import ru.burunsuzian.businessLogic.domain.carOrder.CarOrder;
import ru.burunsuzian.businessLogic.domain.carOrder.Converter;
import ru.burunsuzian.businessLogic.domain.carOrder.CustomOrderState;
import ru.burunsuzian.businessLogic.domain.carOrder.DefaultOrderState;
import ru.burunsuzian.businessLogic.exceptions.CarOrderTransitionException;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.mappers.CarOrderMapper;
import ru.burunsuzian.data.jpa.CarOrderEntity;
import ru.burunsuzian.data.jpa.OutboxMessageEntity;
import ru.burunsuzian.data.persistence.repositories.CarOrderRepository;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;
import ru.burunsuzian.data.persistence.services.CarOrderService;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
@Service
@Slf4j
public class CarOrderServiceImpl implements CarOrderService {
    private CarOrderRepository carOrderRepository;
    private OutboxMessageRepository outboxMessageRepository;
    private CarOrderMapper carOrderMapper;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private Tracer tracer;

    private void propagateJwt(HttpHeaders headers){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth){
            headers.setBearerAuth(jwtAuth.getToken().getTokenValue());
        }
    }

    private void markAsTaken(UUID carId){
        HttpHeaders headers = new HttpHeaders();
        propagateJwt(headers);
        restTemplate.exchange("http://storage-service:8082/cars/" + carId + "/availability?available=false",
                HttpMethod.PATCH,
                new HttpEntity<>(headers),
                Void.class);
    }

    @Override @Transactional
    public CarOrderResponse createOrder(UUID customer, UUID manager, UUID orderedCar) {

        StorageCarDto carInfo = restTemplate.getForObject("http://storage-service:8082/cars/get/" + orderedCar, StorageCarDto.class);

        CarOrder validCarOrder = new CarOrder(UUID.randomUUID(),
                customer,
                manager,
                orderedCar,
                carInfo.isCustomCar(),
                DefaultOrderState.PLACED);
        carOrderRepository.save(carOrderMapper.toEntity(validCarOrder));

        markAsTaken(orderedCar);

        CarOrderResponse carOrderResponse = carOrderMapper.toDto(validCarOrder);

        return carOrderResponse;
    }

    @Override @Transactional
    public CarOrderResponse transitionTo(UUID carOrder, String newState) throws JsonProcessingException, NullPointerException {
        CarOrderEntity carOrderEntity = carOrderRepository.findById(carOrder).orElseThrow(() -> new EntityNotFoundException("Car order not found"));
        CarOrder carOrderDomain = carOrderMapper.toDomain(carOrderEntity);

        boolean isCustomCar = carOrderDomain.isCustomCar();
        var newOrderState = Converter.convert(newState, isCustomCar);

        if (newOrderState == CustomOrderState.WAREHOUSE_APPROVED && isCustomCar) {
            if (outboxMessageRepository.findByProcessedFalse().stream()
                    .filter(msg -> Objects.equals(msg.getCarOrderId(), carOrder))
                    .count() > 0) {
                log.error(" Этот заказ уже обрабатывается");
                throw new CarOrderTransitionException("Заказ в обработке");
            }

            String currentTraceId = MDC.get("traceId");
            String currentSpanId = null;

            WarehouseApprovalRequest request = new WarehouseApprovalRequest(carOrderDomain.getCarOrderId(), currentTraceId, carOrderDomain.isCustomCar());
            OutboxMessageEntity outboxMessageEntity = OutboxMessageEntity.builder()
                    .eventType("WarehouseAcceptanceRequest")
                    .stat("ACTIVE")
                    .carOrderId(carOrderDomain.getCarOrderId())
                    .traceId(currentTraceId)
                    .spanId(currentSpanId)
                    .build();

            outboxMessageEntity.setPayload(objectMapper.writeValueAsString(request));
            outboxMessageRepository.save(outboxMessageEntity);
        }
        else {
            carOrderDomain.transitionTo(newOrderState);
        }
        carOrderRepository.save(carOrderMapper.toEntity(carOrderDomain));

        return carOrderMapper.toDto(carOrderDomain);
    }

    @Override
    public CarOrderResponse getCarOrder(UUID carOrderId) {
        CarOrderEntity ent = carOrderRepository.findById(carOrderId).orElseThrow(() -> new EntityNotFoundException("no such entity"));
        CarOrderResponse response = new CarOrderResponse(
                ent.getId(), ent.getCustomerId(), ent.getManagerId(), ent.getCarId(), ent.getOrderState().getState());
        return response;
    }

    @Override @Transactional
    public void deleteCarOrder(UUID carOrderId) {
        carOrderRepository.deleteById(carOrderId);
    }

    @Override
    public boolean isOwner(String userId, UUID carOrderId) {
        var carOrderEntity = carOrderRepository.findById(carOrderId).orElseThrow(() -> new EntityNotFoundException("no such entity"));
        return userId.equals(carOrderEntity.getCustomerId().toString());
    }

    @Override
    public List<CarOrderResponse> getAllCarOrders() {
        return carOrderRepository.findAll().stream()
                .map(order -> new CarOrderResponse(order.getId(), order.getCustomerId(), order.getManagerId(), order.getCarId(), order.getOrderState().toDomain().toString()))
                .toList();
    }
}