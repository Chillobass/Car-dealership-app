package businessLogic.servicesImpl;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.details.replaceable.Replaceable;
import businessLogic.domain.valueObjects.Money;
import businessLogic.exceptions.IncompatibleComponentException;
import businessLogic.details.Detail;
import data.jpa.replaceableEntities.ReplaceableComponentType;
import data.persistence.repositories.DetailRepository;
import data.persistence.services.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {
    private DetailRepository detailRepository;

    public void swapDetail(Car currentCar, Detail detail){
        if (detail.isCompatibleWith(currentCar.getModel()) == false){
            throw new IncompatibleComponentException("detail " + detail.getId() + " is not compatible with such car");
        }
        currentCar.getConfig().remove(detail.getCategory());
        currentCar.getConfig().put(detail.getCategory(), detail);
    }

    @Override
    public List<ReplaceableComponentType> findAvailableDetails(CarModel model, Replaceable category) {
        return detailRepository
                .findAll().stream()
                .filter(detail -> detail.toDomain().getClass() == (category.getClass()))
                .filter(detail -> new Detail(detail.getId(), detail.toDomain(),
                                                        new Money(detail.getPrice()),
                                                        detail.getCompatibleCars()).isCompatibleWith(model))
                .collect(Collectors.toList());
    }

    @Override
    public Car createConfiguration(Car car, Map<Replaceable, Detail> swaps){
        for (var i : swaps.values()){
            swapDetail(car, i);
        }
        car.setConfigured(true);
        return car;
    }
}
