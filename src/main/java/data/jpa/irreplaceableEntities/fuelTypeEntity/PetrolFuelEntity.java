package data.jpa.irreplaceableEntities.fuelTypeEntity;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.driveType.FrontDrive;
import businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
