package ru.burunsuzian.data.jpa.replaceableEntities;

import ru.burunsuzian.businessLogic.details.replaceable.ManualTransmission;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
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
        return new ManualTransmission(id, reservedBy);
    }
}
