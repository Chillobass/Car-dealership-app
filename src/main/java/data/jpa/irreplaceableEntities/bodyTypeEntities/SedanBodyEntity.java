package data.jpa.irreplaceableEntities.bodyTypeEntities;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.bodyType.SedanBody;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("sedan_body")
public class SedanBodyEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new SedanBody();
    }
}
