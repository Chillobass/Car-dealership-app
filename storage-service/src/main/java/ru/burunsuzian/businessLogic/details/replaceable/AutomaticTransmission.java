package ru.burunsuzian.businessLogic.details.replaceable;

import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.data.jpa.replaceableEntities.AutomaticTransmissionEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutomaticTransmission extends Transmission {

    @Override
    public ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars){
        var entityDetail = new AutomaticTransmissionEntity();
        entityDetail.setPrice(price.getMoney());
        entityDetail.setCompatibleCars(compatibleCars);
        entityDetail.setReservedBy(reservedBy);
        entityDetail.setId(id);
        return entityDetail;
    }

    public AutomaticTransmission(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}
