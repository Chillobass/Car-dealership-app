package ru.burunsuzian.businessLogic.details.replaceable;

import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public abstract class Replaceable {
    protected UUID id;
    protected UUID reservedBy;
    public abstract ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars);
}
