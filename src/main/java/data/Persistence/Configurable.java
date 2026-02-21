package data.Persistence;

import BusinessLogic.CarModel;
import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;

import java.util.List;

public interface Configurable {
    public List<Detail> findAvailableDetails(CarModel model, DetailCategory category);

}
