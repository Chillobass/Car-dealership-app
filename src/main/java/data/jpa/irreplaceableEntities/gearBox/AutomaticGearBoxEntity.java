package data.jpa.irreplaceableEntities.gearBox;

import businessLogic.details.irreplaceable.Irreplaceable;
import businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
