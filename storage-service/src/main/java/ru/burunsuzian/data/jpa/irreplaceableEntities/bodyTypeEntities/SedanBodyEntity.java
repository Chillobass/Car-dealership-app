package ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.SedanBody;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
