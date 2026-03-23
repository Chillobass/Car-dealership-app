package data.persistence.specifications;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.details.irreplaceable.BodyType;
import businessLogic.details.irreplaceable.DriveType;
import businessLogic.details.irreplaceable.FuelType;
import businessLogic.details.irreplaceable.GearBoxType;
import businessLogic.details.replaceable.Transmission;
import businessLogic.domain.valueObjects.Money;

import java.util.UUID;

public class CarSpecifications {

    public static Specification<Car> byBrand(String brand) {
        return car -> car.getModel().getBrand() != null && brand.equals(car.getModel().getBrand());
    }

    public static Specification<CarModel> byModel(String model) {
        return car -> car != null && model.equals(car);
    }

    public static Specification<Car> byPriceLessThanOrEqual(Money maxPrice) {
        return car -> {
            Money carPrice = car.calculatePrice();
            return carPrice != null && carPrice.getMoney().compareTo(maxPrice.getMoney()) <= 0;
        };
    }

    public static Specification<Car> byPriceGreaterThanOrEqual(Money minPrice) {
        return car -> {
            Money carPrice = car.calculatePrice();
            return carPrice != null && carPrice.getMoney().compareTo(minPrice.getMoney()) >= 0;
        };
    }

    public static Specification<Car> byBodyType(BodyType bodyType) {
        return car -> bodyType.equals(car.getModel().getCarBody());
    }

    public static Specification<Car> byFuelType(FuelType fuelType) {
        return car -> fuelType.equals(car.getModel().getFuel());
    }

    public static Specification<Car> byMinEnginePower(Double minPower) {
        return car -> car.getModel().getEnginePower() != null && car.getModel().getEnginePower() >= minPower;
    }

    public static Specification<Car> byEnginePowerBetween(Double min, Double max) {
        return car -> {
            Double power = car.getModel().getEnginePower();
            return power != null && power >= min && power <= max;
        };
    }

    public static Specification<Car> byEngineCapacityBetween(Double min, Double max) {
        return car -> {
            Double capacity = car.getModel().getEngineCapacity();
            return capacity != null && capacity >= min && capacity <= max;
        };
    }

    public static Specification<Car> byGearbox(GearBoxType gearbox) {
        return car -> gearbox.equals(car.getModel().getGearbox());
    }

    public static Specification<Car> byTransmission(Transmission transmission) {
        return car -> car.getConfig().keySet().stream()
                .anyMatch(key -> transmission.getClass().isInstance(key));
    }

    public static Specification<Car> byDrive(DriveType drive) {
        return car -> drive.equals(car.getModel().getCarDrive());
    }

    public static Specification<Car> byColour(String colour) {
        return car -> colour.equalsIgnoreCase(car.getModel().getCarColor());
    }

    public static Specification<Car> byId(UUID id) {
        return car -> id.equals(car.getCarID());
    }
}