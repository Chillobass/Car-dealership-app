package ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.details.irreplaceable.bodyType.UniversalBody;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
