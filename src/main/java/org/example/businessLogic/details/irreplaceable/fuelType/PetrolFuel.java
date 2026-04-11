package org.example.businessLogic.details.irreplaceable.fuelType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;

public class PetrolFuel extends FuelType {
    public IrreplaceableComponentType toEntity(){
        return new PetrolFuelEntity();
    }
}
