package businessLogic;

import businessLogic.details.replaceable.Replaceable;
import businessLogic.exceptions.IncompatibleComponentException;
import businessLogic.details.Detail;
import data.persistence.Configurable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationService implements Configurable {
    private Context context;

    public ConfigurationService(Context contxt) {
        context = contxt;
    }
    public void swapDetail(Car currentCar, Detail detail){
        if (detail.isCompatibleWith(currentCar.getModel()) == false){
            throw new IncompatibleComponentException("detail " + detail.getId() + " is not compatible with " + currentCar.getModel().getModelName());
        }
        currentCar.getConfig().remove(detail.getCategory());
        currentCar.getConfig().put(detail.getCategory(), detail);
    }

    @Override
    public List<Detail> findAvailableDetails(CarModel model, Replaceable category) {
        return context.getDetailRepository()
                .findByCategory(category).stream()
                .filter(detail -> detail.isCompatibleWith(model))
                .collect(Collectors.toList());
    }

    @Override
    public Car createConfiguration(Car car, Map<Replaceable, Detail> swaps){
        for (var i : swaps.values()){
            swapDetail(car, i);
        }
        return car;
    }
}
