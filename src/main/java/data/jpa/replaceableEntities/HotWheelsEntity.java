package data.jpa.replaceableEntities;

import businessLogic.details.replaceable.HotWheels;
import businessLogic.details.replaceable.Replaceable;
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
        return new HotWheels();
    }
}
