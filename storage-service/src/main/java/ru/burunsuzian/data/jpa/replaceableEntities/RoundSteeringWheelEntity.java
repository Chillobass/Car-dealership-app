package ru.burunsuzian.data.jpa.replaceableEntities;

import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.businessLogic.details.replaceable.RoundSteeringWheel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("round_steering_wheel")
public class RoundSteeringWheelEntity extends ReplaceableComponentType {
    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Replaceable toDomain() {
        return new RoundSteeringWheel(id, reservedBy);
    }
}
