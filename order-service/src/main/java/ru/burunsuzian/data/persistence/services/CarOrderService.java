package ru.burunsuzian.data.persistence.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.burunsuzian.application.responses.CarOrderResponse;

import java.util.List;
import java.util.UUID;

public interface CarOrderService {
    CarOrderResponse createOrder(UUID customer, UUID manager, UUID orderedCar);

    CarOrderResponse transitionTo(UUID carOrder, String newState) throws JsonProcessingException;

    CarOrderResponse getCarOrder(UUID carOrderId);

    void deleteCarOrder(UUID carOrderId);

    boolean isOwner(String userId, UUID carOrderId);

    List<CarOrderResponse> getAllCarOrders();
}
