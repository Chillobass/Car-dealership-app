package org.example.businessLogic.details.replaceable;

import org.example.businessLogic.domain.valueObjects.Money;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public interface Replaceable {
    ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars);
}
