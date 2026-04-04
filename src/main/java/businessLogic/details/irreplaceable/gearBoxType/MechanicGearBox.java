package businessLogic.details.irreplaceable.gearBoxType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.gearBox.MechanicGearBoxEntity;

public class MechanicGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new MechanicGearBoxEntity();
    }
}
