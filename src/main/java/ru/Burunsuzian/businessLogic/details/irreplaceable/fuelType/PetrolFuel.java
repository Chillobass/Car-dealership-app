package ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;

public class PetrolFuel extends FuelType {
    public IrreplaceableComponentType toEntity(){
        return new PetrolFuelEntity();
    }
}
