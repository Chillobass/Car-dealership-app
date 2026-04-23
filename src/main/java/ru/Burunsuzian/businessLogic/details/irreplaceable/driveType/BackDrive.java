package ru.Burunsuzian.businessLogic.details.irreplaceable.driveType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.BackDriveEntity;

public class BackDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new BackDriveEntity();
    }
}
