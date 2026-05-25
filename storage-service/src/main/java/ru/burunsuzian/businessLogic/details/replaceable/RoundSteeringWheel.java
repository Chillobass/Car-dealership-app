package ru.burunsuzian.businessLogic.details.replaceable;

import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.data.jpa.replaceableEntities.RoundSteeringWheelEntity;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;

import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoundSteeringWheel extends SteeringWheel {

    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new RoundSteeringWheelEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        entityDetail.setReservedBy(reservedBy);
        entityDetail.setId(id);
        return entityDetail;
    }

    public RoundSteeringWheel(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}
