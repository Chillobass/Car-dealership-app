package org.example.businessLogic.details.irreplaceable.gearBoxType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;

public class AutomaticGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new AutomaticGearBoxEntity();
    }
}
