package ru.burunsuzian.businessLogic.details.replaceable;

import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.data.jpa.replaceableEntities.BestDecorEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

@Setter
@Getter
public class BestDecor extends Decor {
    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new BestDecorEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        entityDetail.setReservedBy(reservedBy);
        entityDetail.setId(id);
        return entityDetail;
    }

    public BestDecor(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}
