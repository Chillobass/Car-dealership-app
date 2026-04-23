package ru.Burunsuzian.businessLogic.details;

import ru.Burunsuzian.businessLogic.CarModel;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter @AllArgsConstructor @Builder
public class Detail {
    private UUID id;
    private Replaceable category;
    private Money price;
    private Set<UUID> compatibleModels;

    public boolean isCompatibleWith(CarModel model) {
        return compatibleModels.contains(model.getModelId());
    }
}