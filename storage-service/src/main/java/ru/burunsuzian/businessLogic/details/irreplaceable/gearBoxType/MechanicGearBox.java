package ru.burunsuzian.businessLogic.details.irreplaceable.gearBoxType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.gearBox.MechanicGearBoxEntity;

public class MechanicGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new MechanicGearBoxEntity();
    }
}
