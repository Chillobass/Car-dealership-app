package ru.Burunsuzian.businessLogic.details.irreplaceable.gearBoxType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;

public class AutomaticGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new AutomaticGearBoxEntity();
    }
}
