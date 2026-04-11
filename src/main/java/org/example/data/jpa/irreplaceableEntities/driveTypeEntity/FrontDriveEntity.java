package org.example.data.jpa.irreplaceableEntities.driveTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.driveType.FrontDrive;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
