package ru.burunsuzian.data.jpa.replaceableEntities;

import ru.burunsuzian.businessLogic.details.replaceable.HotWheels;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("hot_wheels")
public class HotWheelsEntity extends ReplaceableComponentType {
    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Replaceable toDomain() {
        return new HotWheels(id, reservedBy);
    }
}
