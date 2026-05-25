package ru.burunsuzian.data.jpa.irreplaceableEntities;

import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ru.burunsuzian.data.jpa.BaseJpaEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.CoupeBodyEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.SedanBodyEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.BackDriveEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FrontDriveEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.ElectricFuelEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.gearBox.MechanicGearBoxEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "component_types")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "irreplaceable_component_type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MechanicGearBoxEntity.class, name = "mechanic_gear_box"),
        @JsonSubTypes.Type(value = AutomaticGearBoxEntity.class, name = "automatic_gear_box"),
        @JsonSubTypes.Type(value = PetrolFuelEntity.class, name = "petrol_fuel"),
        @JsonSubTypes.Type(value = ElectricFuelEntity.class, name = "electric_fuel"),
        @JsonSubTypes.Type(value = DieselFuelEntity.class, name = "diesel_fuel"),
        @JsonSubTypes.Type(value = FullDriveEntity.class, name = "full_drive"),
        @JsonSubTypes.Type(value = FrontDriveEntity.class, name = "front_drive"),
        @JsonSubTypes.Type(value = BackDriveEntity.class, name = "back_drive"),
        @JsonSubTypes.Type(value = UniversalBodyEntity.class, name = "universal_body"),
        @JsonSubTypes.Type(value = SedanBodyEntity.class, name = "sedan_body"),
        @JsonSubTypes.Type(value = CoupeBodyEntity.class, name = "coupe_body")
})

public abstract class IrreplaceableComponentType extends BaseJpaEntity {
    public boolean isRequired() {
        return true;
    }

    public abstract Irreplaceable toDomain();
}
