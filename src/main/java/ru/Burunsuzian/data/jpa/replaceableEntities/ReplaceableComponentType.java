package ru.Burunsuzian.data.jpa.replaceableEntities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.Burunsuzian.data.jpa.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "replaceable_component_type")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "replaceable_component_type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BestDecorEntity.class, name = "best_decor"),
        @JsonSubTypes.Type(value = RoundSteeringWheelEntity.class, name = "round_steering_wheel"),
        @JsonSubTypes.Type(value = ManualTransmissionEntity.class, name = "manual_transmission_entity"),
        @JsonSubTypes.Type(value = AutomaticTransmissionEntity.class, name = "automatic_transmission_entity"),
        @JsonSubTypes.Type(value = HotWheelsEntity.class, name = "hot_wheels")
})

@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "replaceable_component_id"))
public abstract class ReplaceableComponentType extends BaseJpaEntity {
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(
            name = "replaceable_component_type_compatible_cars",
            joinColumns = @JoinColumn(
                    name = "replaceable_component_type_id",
                    referencedColumnName = "replaceable_component_id"
            )
    )
    @Column(name = "compatible_cars")
    private Set<UUID> compatibleCars;
    public abstract boolean isRequired();
    public abstract Replaceable toDomain();
}
