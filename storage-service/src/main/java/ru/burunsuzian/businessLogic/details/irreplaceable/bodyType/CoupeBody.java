package ru.burunsuzian.businessLogic.details.irreplaceable.bodyType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.CoupeBodyEntity;

public class CoupeBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new CoupeBodyEntity();
    }
}
