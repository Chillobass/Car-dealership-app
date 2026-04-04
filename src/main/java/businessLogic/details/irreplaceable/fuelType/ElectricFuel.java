package businessLogic.details.irreplaceable.fuelType;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.fuelTypeEntity.ElectricFuelEntity;

public class ElectricFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new ElectricFuelEntity();
    }
}
