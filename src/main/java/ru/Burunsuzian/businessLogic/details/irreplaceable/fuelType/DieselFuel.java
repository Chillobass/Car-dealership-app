package ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;

public class DieselFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new DieselFuelEntity();
    }
}
