package businessLogic.details.irreplaceable.driveType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.driveTypeEntity.BackDriveEntity;

public class BackDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new BackDriveEntity();
    }
}
