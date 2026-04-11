package org.example.businessLogic.details.replaceable;

import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.ManualTransmissionEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

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