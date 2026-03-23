package businessLogic.details;

import businessLogic.CarModel;
import businessLogic.details.replaceable.Replaceable;
import businessLogic.domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter @AllArgsConstructor @Builder
public class Detail {
    private UUID id;
    private Replaceable category;
    private Money price;
    private List<CarModel> compatibleModels;

    public boolean isCompatibleWith(CarModel model) {
        return compatibleModels.contains(model);
    }
}