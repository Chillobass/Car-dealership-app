package ru.Burunsuzian.data.persistence.services;

import ru.Burunsuzian.application.requests.CarCreationRequest;
import ru.Burunsuzian.application.responses.CarResponse;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarResponse getCar(UUID carId);

    List<ReplaceableComponentType> findAvailableDetails(UUID carId, String category);

    CarResponse createConfiguration(UUID carId, UUID decor, UUID steeringWheel, UUID transmission, UUID wheels);

    void deleteCar(UUID carId);

    CarResponse createCar(CarCreationRequest request);
}
