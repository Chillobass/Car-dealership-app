package ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("petrol_fuel")
public class PetrolFuelEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new PetrolFuel();
    }
}
