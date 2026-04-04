package data.jpa.irreplaceableEntities.bodyTypeEntities;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.bodyType.UniversalBody;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("universal_body")
public class UniversalBodyEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new UniversalBody();
    }
}
