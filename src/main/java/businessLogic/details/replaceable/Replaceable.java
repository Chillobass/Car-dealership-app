package businessLogic.details.replaceable;

import businessLogic.domain.valueObjects.Money;
import data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

public interface Replaceable {
    ReplaceableComponentType toEntity(Money price, Set<UUID> compatibleCars);
}
