package data.jpa.replaceableEntities;

import businessLogic.details.replaceable.AutomaticTransmission;
import businessLogic.details.replaceable.Replaceable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("automatic_transmission_entity")
public class AutomaticTransmissionEntity extends ReplaceableComponentType {
    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Replaceable toDomain() {
        return new AutomaticTransmission();
    }
}
