package ru.Burunsuzian.businessLogic.details.irreplaceable.driveType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;

public class FullDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FullDriveEntity();
    }
}
