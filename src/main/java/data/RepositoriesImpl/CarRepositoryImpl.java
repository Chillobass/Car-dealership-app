package data.RepositoriesImpl;

import BusinessLogic.Car;
import data.Persistence.Repositories.CarFilter;
import data.Persistence.Repositories.CarRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CarRepositoryImpl implements CarRepository {
    private final HashMap<UUID, Car> storage = new HashMap<>(); // final ли ??

    @Override
    public Car add(Car car) {
        storage.put(car.getCarID(), car);
        return car;
    }

    @Override
    public List<Car> query(CarFilter carFilter) {
        return storage.values().stream()
                .filter(car -> matches(car, carFilter))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID carID) {
        storage.remove(carID);
    }

    private boolean matches(Car car, CarFilter filter){
        if (filter == null) return true;

        if (filter.getMinPrice() != null && car.getFinalPrice().getMoney().compareTo(filter.getMinPrice().getMoney()) < 0) return false;
        if (filter.getMaxPrice() != null && car.getFinalPrice().getMoney().compareTo(filter.getMaxPrice().getMoney()) > 0) return false;

        if (filter.getBrand() != null && !filter.getBrand().equalsIgnoreCase(car.getModel().getBrand())) return false;

        if (filter.getModel() != null && !filter.getModel().equalsIgnoreCase(car.getModel().getModelName())) return false;

        if (filter.getBodyType() != null && filter.getBodyType() != car.getCarBody()) return false;

        if (filter.getFuelType() != null && filter.getFuelType() != car.getFuel()) return false;

        if (filter.getMinEnginePower() != null && car.getEnginePower() < filter.getMinEnginePower()) return false;
        if (filter.getMaxEnginePower() != null && car.getEnginePower() > filter.getMaxEnginePower()) return false;

        if (filter.getMinEngineVolume() != null && car.getEngineCapacity() < filter.getMinEngineVolume()) return false;
        if (filter.getMaxEngineVolume() != null && car.getEngineCapacity() > filter.getMaxEngineVolume()) return false;

        if (filter.getTransmission() != null && filter.getTransmission() != car.getTransmission()) return false;

        if (filter.getDrive() != null && filter.getDrive() != car.getCarDrive()) return false;

        if (filter.getColor() != null && !filter.getColor().equalsIgnoreCase(car.getCarColour())) return false;

        return true;
    }
}
