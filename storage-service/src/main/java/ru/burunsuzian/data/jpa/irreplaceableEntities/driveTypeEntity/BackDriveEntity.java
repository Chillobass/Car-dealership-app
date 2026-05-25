package ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.driveType.BackDrive;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
