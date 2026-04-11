package org.example.businessLogic.details.irreplaceable.driveType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.driveTypeEntity.BackDriveEntity;

public class BackDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new BackDriveEntity();
    }
}
