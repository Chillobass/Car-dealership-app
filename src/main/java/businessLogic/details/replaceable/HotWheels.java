package businessLogic.details.replaceable;

import businessLogic.domain.valueObjects.Money;
import data.jpa.replaceableEntities.BestDecorEntity;
import data.jpa.replaceableEntities.HotWheelsEntity;
import data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public class HotWheels extends Wheels {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new HotWheelsEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        return entityDetail;
    }
}
