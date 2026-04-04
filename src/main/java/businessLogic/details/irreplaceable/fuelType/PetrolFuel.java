package businessLogic.details.irreplaceable.fuelType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;

public class PetrolFuel extends FuelType {
    public IrreplaceableComponentType toEntity(){
        return new PetrolFuelEntity();
    }
}
