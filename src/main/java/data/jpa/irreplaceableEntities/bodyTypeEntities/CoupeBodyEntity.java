package data.jpa.irreplaceableEntities.bodyTypeEntities;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.bodyType.CoupeBody;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("coupe_body")
public class CoupeBodyEntity extends IrreplaceableComponentType {

    @Override
    public Irreplaceable toDomain() {
        return new CoupeBody();
    }
}
