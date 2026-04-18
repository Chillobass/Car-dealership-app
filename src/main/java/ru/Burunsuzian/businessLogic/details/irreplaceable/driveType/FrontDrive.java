package ru.Burunsuzian.businessLogic.details.irreplaceable.driveType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FrontDriveEntity;

public class FrontDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FrontDriveEntity();
    }
}
