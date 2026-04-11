package org.example.data.persistence.services;

import org.example.application.requests.CarCreationRequest;
import org.example.application.responses.CarResponse;
import org.example.businessLogic.CarModel;
import org.example.businessLogic.details.replaceable.Replaceable;
import org.example.data.jpa.domainEntities.DetailEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarResponse getCar(UUID carId);

    List<ReplaceableComponentType> findAvailableDetails(UUID carId, String category);

    CarResponse createConfiguration(UUID carId, UUID decor, UUID steeringWheel, UUID transmission, UUID wheels);

    void deleteCar(UUID carId);

    CarResponse createCar(CarCreationRequest request);
}
