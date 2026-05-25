package ru.burunsuzian.businessLogic.details.irreplaceable.bodyType;

import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;

public class UniversalBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new UniversalBodyEntity();
    }
}
