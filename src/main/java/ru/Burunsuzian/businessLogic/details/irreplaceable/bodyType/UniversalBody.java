package ru.Burunsuzian.businessLogic.details.irreplaceable.bodyType;

import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;

public class UniversalBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new UniversalBodyEntity();
    }
}
