package data.jpa.replaceableEntities;

import businessLogic.details.replaceable.Replaceable;
import businessLogic.details.replaceable.RoundSteeringWheel;
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
        return new RoundSteeringWheel();
    }
}
