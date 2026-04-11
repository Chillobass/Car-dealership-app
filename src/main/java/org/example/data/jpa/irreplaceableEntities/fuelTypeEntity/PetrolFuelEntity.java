package org.example.data.jpa.irreplaceableEntities.fuelTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("petrol_fuel")
public class PetrolFuelEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new PetrolFuel();
    }
}
