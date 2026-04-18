package ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.bodyType.UniversalBody;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
