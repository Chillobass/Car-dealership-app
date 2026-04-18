package ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("automatic_gear_box")
public class AutomaticGearBoxEntity extends IrreplaceableComponentType {
    @Override
    public Irreplaceable toDomain() {
        return new AutomaticGearBox();
    }
}
