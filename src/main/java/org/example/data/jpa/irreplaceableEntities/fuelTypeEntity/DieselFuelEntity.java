package org.example.data.jpa.irreplaceableEntities.fuelTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.fuelType.DieselFuel;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("diesel_fuel")
public class DieselFuelEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new DieselFuel();
    }
}
