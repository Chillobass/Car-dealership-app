package ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType.ElectricFuel;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
