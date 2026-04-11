package org.example.data.jpa.irreplaceableEntities.gearBox;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
