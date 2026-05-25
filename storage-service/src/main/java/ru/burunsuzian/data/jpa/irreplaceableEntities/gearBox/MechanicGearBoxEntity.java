package ru.burunsuzian.data.jpa.irreplaceableEntities.gearBox;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
