package data.jpa.irreplaceableEntities.driveTypeEntity;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.bodyType.UniversalBody;
import businessLogic.details.irreplaceable.driveType.BackDrive;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("back_drive")
public class BackDriveEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new BackDrive();
    }
}
