package ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
