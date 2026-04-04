package businessLogic.details.replaceable;

import businessLogic.domain.valueObjects.Money;
import data.jpa.replaceableEntities.AutomaticTransmissionEntity;
import data.jpa.replaceableEntities.ReplaceableComponentType;

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
