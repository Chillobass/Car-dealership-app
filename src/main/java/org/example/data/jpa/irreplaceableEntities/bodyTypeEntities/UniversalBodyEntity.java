package org.example.data.jpa.irreplaceableEntities.bodyTypeEntities;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.bodyType.UniversalBody;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
