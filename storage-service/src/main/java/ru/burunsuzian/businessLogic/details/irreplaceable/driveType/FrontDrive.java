package ru.burunsuzian.businessLogic.details.irreplaceable.driveType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FrontDriveEntity;

public class FrontDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FrontDriveEntity();
    }
}
