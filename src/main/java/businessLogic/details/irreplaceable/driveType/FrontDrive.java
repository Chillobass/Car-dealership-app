package businessLogic.details.irreplaceable.driveType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.driveTypeEntity.FrontDriveEntity;

public class FrontDrive extends DriveType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new FrontDriveEntity();
    }
}
