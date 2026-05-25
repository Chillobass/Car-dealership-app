package ru.burunsuzian.businessLogic.details.irreplaceable.gearBoxType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;

public class AutomaticGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new AutomaticGearBoxEntity();
    }
}
