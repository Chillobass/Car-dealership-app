package org.example.businessLogic.details.irreplaceable.driveType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;

public class FullDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FullDriveEntity();
    }
}
