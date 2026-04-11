package org.example.businessLogic.details;

import org.example.businessLogic.CarModel;
import org.example.businessLogic.details.replaceable.Replaceable;
import org.example.businessLogic.domain.valueObjects.Money;
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