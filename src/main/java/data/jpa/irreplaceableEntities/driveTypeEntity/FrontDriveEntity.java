package data.jpa.irreplaceableEntities.driveTypeEntity;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.driveType.BackDrive;
import businessLogic.details.irreplaceable.driveType.FrontDrive;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("front_drive")
public class FrontDriveEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new FrontDrive();
    }
}
