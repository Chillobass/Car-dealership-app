package ru.Burunsuzian.businessLogic.details.replaceable;

import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public interface Replaceable {
    ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars);
}
