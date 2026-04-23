package ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
