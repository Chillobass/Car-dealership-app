package businessLogic.details.replaceable;

import businessLogic.domain.valueObjects.Money;
import data.jpa.replaceableEntities.AutomaticTransmissionEntity;
import data.jpa.replaceableEntities.BestDecorEntity;
import data.jpa.replaceableEntities.ReplaceableComponentType;

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
