package ru.Burunsuzian.businessLogic.details.irreplaceable.gearBoxType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox.MechanicGearBoxEntity;

public class MechanicGearBox extends GearBoxType {
    @Override
    public IrreplaceableComponentType toEntity(){
        return new MechanicGearBoxEntity();
    }
}
