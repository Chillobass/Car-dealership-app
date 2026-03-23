package data.persistence.repositories;

import businessLogic.Car;
import data.persistence.specifications.Specification;

import java.util.List;
import java.util.UUID;

public interface ModelsRepository {
    Car add(Car carModel);

    List<Car> query(Specification<Car> specification);

    void delete(UUID carID);
}
