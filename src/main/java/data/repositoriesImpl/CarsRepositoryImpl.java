package data.repositoriesImpl;

import businessLogic.Car;
import businessLogic.CarModel;
import data.persistence.repositories.ModelsRepository;
import data.persistence.specifications.Specification;

import java.util.*;

public class CarsRepositoryImpl implements ModelsRepository {
    private final Map<UUID, Car> storage = new HashMap<>();

    @Override
    public Car add(Car car) {
        storage.put(car.getCarID(), car);
        return car;
    }

    public boolean contains(Car car){
        return storage.containsValue(car);
    }

    @Override
    public void delete(UUID carID){
        storage.remove(carID);
    }

    @Override
    public List<Car> query(Specification<Car> specification){
        return storage.values().stream()
                .filter(carModel -> specification.isSatisfiedBy(carModel))
                .toList();
    }
}
