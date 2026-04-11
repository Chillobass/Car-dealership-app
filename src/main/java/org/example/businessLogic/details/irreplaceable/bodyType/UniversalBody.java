package org.example.businessLogic.details.irreplaceable.bodyType;

import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.example.data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;

public class UniversalBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new UniversalBodyEntity();
    }
}
