package org.example.businessLogic.details.replaceable;

import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.BestDecorEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public class BestDecor extends Decor {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new BestDecorEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        return entityDetail;
    }
}
