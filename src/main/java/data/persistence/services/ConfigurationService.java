package data.persistence.services;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;
import data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.List;
import java.util.Map;

public interface ConfigurationService {
    List<ReplaceableComponentType> findAvailableDetails(CarModel model, Replaceable category);

    Car createConfiguration(Car car, Map<Replaceable, Detail> swaps);
}
