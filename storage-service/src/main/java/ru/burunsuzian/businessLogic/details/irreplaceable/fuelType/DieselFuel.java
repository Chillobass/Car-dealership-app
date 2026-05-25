package ru.burunsuzian.businessLogic.details.irreplaceable.fuelType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;

public class DieselFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new DieselFuelEntity();
    }
}
