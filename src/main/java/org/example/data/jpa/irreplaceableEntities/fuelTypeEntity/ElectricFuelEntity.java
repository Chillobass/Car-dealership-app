package org.example.data.jpa.irreplaceableEntities.fuelTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.fuelType.ElectricFuel;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("electric_fuel")
public class ElectricFuelEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new ElectricFuel();
    }
}
