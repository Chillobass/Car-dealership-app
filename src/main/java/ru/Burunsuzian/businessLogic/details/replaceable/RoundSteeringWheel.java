package ru.Burunsuzian.businessLogic.details.replaceable;

import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.Burunsuzian.data.jpa.replaceableEntities.RoundSteeringWheelEntity;

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
