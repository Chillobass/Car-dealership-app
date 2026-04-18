package ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.ElectricFuelEntity;

public class ElectricFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new ElectricFuelEntity();
    }
}
