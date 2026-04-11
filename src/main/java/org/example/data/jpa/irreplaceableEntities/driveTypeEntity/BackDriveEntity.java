package org.example.data.jpa.irreplaceableEntities.driveTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.driveType.BackDrive;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
