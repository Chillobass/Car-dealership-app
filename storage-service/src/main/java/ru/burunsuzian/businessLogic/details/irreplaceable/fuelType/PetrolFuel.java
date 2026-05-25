package ru.burunsuzian.businessLogic.details.irreplaceable.fuelType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;

public class PetrolFuel extends FuelType {
    public IrreplaceableComponentType toEntity(){
        return new PetrolFuelEntity();
    }
}
