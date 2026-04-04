package businessLogic.details.irreplaceable.bodyType;

import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import data.jpa.irreplaceableEntities.bodyTypeEntities.CoupeBodyEntity;

public class CoupeBody extends BodyType{
    @Override
    public IrreplaceableComponentType toEntity(){
        return new CoupeBodyEntity();
    }
}
