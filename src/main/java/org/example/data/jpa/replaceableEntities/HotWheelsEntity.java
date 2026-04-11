package org.example.data.jpa.replaceableEntities;

import org.example.businessLogic.details.replaceable.HotWheels;
import org.example.businessLogic.details.replaceable.Replaceable;
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
