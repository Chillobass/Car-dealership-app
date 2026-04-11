package org.example.data.jpa.replaceableEntities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import org.example.businessLogic.details.replaceable.AutomaticTransmission;
import org.example.businessLogic.details.replaceable.Replaceable;
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
