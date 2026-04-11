package org.example.data.persistence.services;

import org.example.application.requests.CarOrderRequest;
import org.example.application.responses.CarOrderResponse;
import org.example.businessLogic.domain.carOrder.OrderState;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CarOrderService {
    ResponseEntity<CarOrderResponse> createOrder(UUID customer, UUID manager, UUID orderedCar);

    ResponseEntity<CarOrderResponse> transitionTo(UUID carOrder, String newState);

    void delete(UUID id);
}
