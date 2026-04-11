package org.example.businessLogic.details.irreplaceable.fuelType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;

public class DieselFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new DieselFuelEntity();
    }
}
