package ru.Burunsuzian.data.persistence.services;

import ru.Burunsuzian.application.responses.CarOrderResponse;

import java.util.UUID;

public interface CarOrderService {
    CarOrderResponse createOrder(UUID customer, UUID manager, UUID orderedCar);

    CarOrderResponse transitionTo(UUID carOrder, String newState);

    CarOrderResponse getCarOrder(UUID carOrderId);

    void deleteCarOrder(UUID carOrderId);

    void delete(UUID id);
}
