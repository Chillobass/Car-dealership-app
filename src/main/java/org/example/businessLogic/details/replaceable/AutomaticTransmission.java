package org.example.businessLogic.details.replaceable;

import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.AutomaticTransmissionEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public class AutomaticTransmission extends Transmission {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new AutomaticTransmissionEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        return entityDetail;
    }
}
