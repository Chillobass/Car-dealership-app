package BusinessLogic.details;

import BusinessLogic.CarModel;
import data.Domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter @AllArgsConstructor
public class Detail {
    private UUID id;
    private DetailCategory category;
    private String name;
    private Money price;
    private List<CarModel> compatibleModels;

    public boolean isCompatibleWith(CarModel model) {
        return compatibleModels.contains(model);
    }
}
