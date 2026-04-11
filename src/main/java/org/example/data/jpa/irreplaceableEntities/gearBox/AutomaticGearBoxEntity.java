package org.example.data.jpa.irreplaceableEntities.gearBox;

import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import org.example.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
