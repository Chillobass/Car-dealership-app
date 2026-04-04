package businessLogic.details.irreplaceable.fuelType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;

public class DieselFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new DieselFuelEntity();
    }
}
