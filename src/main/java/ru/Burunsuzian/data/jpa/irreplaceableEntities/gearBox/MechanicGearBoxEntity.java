package ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("mechanic_gear_box")
public class MechanicGearBoxEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new PetrolFuel();
    }
}
