package ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.driveType.FullDrive;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
