package ru.Burunsuzian.data.jpa.replaceableEntities;

import ru.Burunsuzian.businessLogic.details.replaceable.ManualTransmission;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
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
