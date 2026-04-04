package businessLogic.details.irreplaceable.driveType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;

public class FullDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FullDriveEntity();
    }
}
