package BusinessLogic;

import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;
import data.Persistence.Configurable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationService implements Configurable {
    private Context context;

    public ConfigurationService(Context contxt) {
        context = contxt;
    }

    @Override
    public List<Detail> findAvailableDetails(CarModel model, DetailCategory category) {
        return context.getDetailRepository()
                .findByCategoty(category).stream()
                .filter(detail -> detail.isCompatibleWith(model))
                .collect(Collectors.toList());
    }

    public CarConfiguration createConfiguration(Car car, Map<DetailCategory, Detail> swaps){
        CarConfiguration config = new CarConfiguration(car);
        for (var i : swaps.values()){
            config.swapDetail(i);
        }
        return config;
    }
}
