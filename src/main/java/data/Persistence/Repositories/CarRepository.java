package data.Persistence.Repositories;

import BusinessLogic.Car;

import java.util.List;
import java.util.UUID;

public interface CarRepository {
    Car add(Car Car);

    List<Car> query(CarFilter carFilter);

    void delete(UUID carID);
}
