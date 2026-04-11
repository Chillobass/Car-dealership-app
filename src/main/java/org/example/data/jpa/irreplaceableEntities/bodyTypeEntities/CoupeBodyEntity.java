package org.example.data.jpa.irreplaceableEntities.bodyTypeEntities;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
