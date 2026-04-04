package businessLogic.details.irreplaceable.bodyType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;

public class UniversalBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new UniversalBodyEntity();
    }
}
