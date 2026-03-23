package data.persistence;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;

import java.util.List;
import java.util.Map;

public interface Configurable {
    List<Detail> findAvailableDetails(CarModel model, Replaceable category);

    Car createConfiguration(Car car, Map<Replaceable, Detail> swaps);
}
