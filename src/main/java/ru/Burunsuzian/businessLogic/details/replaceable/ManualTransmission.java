package ru.Burunsuzian.businessLogic.details.replaceable;

import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.data.jpa.replaceableEntities.ManualTransmissionEntity;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public class ManualTransmission extends Transmission {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new ManualTransmissionEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        return entityDetail;
    }
}