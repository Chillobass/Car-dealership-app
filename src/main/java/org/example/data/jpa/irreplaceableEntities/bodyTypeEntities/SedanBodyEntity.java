package org.example.data.jpa.irreplaceableEntities.bodyTypeEntities;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.bodyType.SedanBody;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
