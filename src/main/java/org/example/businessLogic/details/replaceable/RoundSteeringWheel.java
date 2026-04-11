package org.example.businessLogic.details.replaceable;

import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;
import org.example.data.jpa.replaceableEntities.RoundSteeringWheelEntity;

import java.util.Set;
import java.util.UUID;

public class RoundSteeringWheel extends SteeringWheel {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new RoundSteeringWheelEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        return entityDetail;
    }
}
