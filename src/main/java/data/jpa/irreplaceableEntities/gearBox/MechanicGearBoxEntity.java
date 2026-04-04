package data.jpa.irreplaceableEntities.gearBox;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
