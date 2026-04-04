package businessLogic.details.irreplaceable.gearBoxType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;

public class AutomaticGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new AutomaticGearBoxEntity();
    }
}
