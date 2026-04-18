package org.example.data.persistence.services;

import org.example.application.requests.CarOrderRequest;
import org.example.application.responses.CarOrderResponse;
import org.example.businessLogic.domain.carOrder.OrderState;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CarOrderService {
    CarOrderResponse createOrder(UUID customer, UUID manager, UUID orderedCar);

    CarOrderResponse transitionTo(UUID carOrder, String newState);

    CarOrderResponse getCarOrder(UUID carOrderId);

    void deleteCarOrder(UUID carOrderId);

    void delete(UUID id);
}
