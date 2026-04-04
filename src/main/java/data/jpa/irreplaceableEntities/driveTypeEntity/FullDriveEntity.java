package data.jpa.irreplaceableEntities.driveTypeEntity;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.driveType.FrontDrive;
import businessLogic.details.irreplaceable.driveType.FullDrive;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("full_drive")
public class FullDriveEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new FullDrive();
    }
}
