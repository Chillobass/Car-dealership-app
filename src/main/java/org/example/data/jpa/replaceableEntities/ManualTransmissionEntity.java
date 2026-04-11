package org.example.data.jpa.replaceableEntities;

import org.example.businessLogic.details.replaceable.ManualTransmission;
import org.example.businessLogic.details.replaceable.Replaceable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("manual_transmission_entity")
public class ManualTransmissionEntity extends ReplaceableComponentType {
    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Replaceable toDomain() {
        return new ManualTransmission();
    }
}
