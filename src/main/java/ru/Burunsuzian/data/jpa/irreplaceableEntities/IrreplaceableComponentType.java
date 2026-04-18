package ru.Burunsuzian.data.jpa.irreplaceableEntities;

import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ru.Burunsuzian.data.jpa.BaseJpaEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.CoupeBodyEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.SedanBodyEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.bodyTypeEntities.UniversalBodyEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.BackDriveEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FrontDriveEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.driveTypeEntity.FullDriveEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.DieselFuelEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.ElectricFuelEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.fuelTypeEntity.PetrolFuelEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox.AutomaticGearBoxEntity;
import ru.Burunsuzian.data.jpa.irreplaceableEntities.gearBox.MechanicGearBoxEntity;
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
