package org.example.data.jpa.irreplaceableEntities.driveTypeEntity;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.driveType.FullDrive;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
