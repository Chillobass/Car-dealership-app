package org.example.businessLogic.details.irreplaceable.fuelType;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.fuelTypeEntity.ElectricFuelEntity;

public class ElectricFuel extends FuelType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new ElectricFuelEntity();
    }
}
