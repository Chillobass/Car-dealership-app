package businessLogic.details.irreplaceable.bodyType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.bodyTypeEntities.SedanBodyEntity;

public class SedanBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new SedanBodyEntity();
    }
}
