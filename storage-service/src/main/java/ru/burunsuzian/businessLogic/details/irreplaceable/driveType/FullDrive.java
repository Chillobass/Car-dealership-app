package ru.burunsuzian.businessLogic.details.irreplaceable.driveType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;

public class FullDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FullDriveEntity();
    }
}
