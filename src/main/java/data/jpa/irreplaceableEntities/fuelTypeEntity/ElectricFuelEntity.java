package data.jpa.irreplaceableEntities.fuelTypeEntity;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.fuelType.DieselFuel;
import businessLogic.details.irreplaceable.fuelType.ElectricFuel;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("electric_fuel")
public class ElectricFuelEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new ElectricFuel();
    }
}
