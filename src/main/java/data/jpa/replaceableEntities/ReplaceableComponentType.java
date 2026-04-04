package data.jpa.replaceableEntities;

import businessLogic.details.replaceable.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import data.jpa.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.*;

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
public abstract class ReplaceableComponentType extends BaseJpaEntity {
    private BigDecimal price;
    @ElementCollection
    private Set<UUID> compatibleCars;
    public abstract boolean isRequired();
    public abstract Replaceable toDomain();
}
