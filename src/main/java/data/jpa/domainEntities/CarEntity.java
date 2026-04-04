package data.jpa.domainEntities;

import data.jpa.BaseJpaEntity;
import data.jpa.replaceableEntities.ReplaceableComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Table(name="cars")
@AttributeOverride(name = "id", column = @Column(name = "car_id"))
public class CarEntity extends BaseJpaEntity {
    @ManyToOne @JoinColumn(name="model_name")
    private CarModelEntity model;

    @Column(name="steering_wheel")
    private ReplaceableComponentType steeringWheel;

    @Column(name="wheels")
    private ReplaceableComponentType wheels;

    @Column(name="transmission")
    private ReplaceableComponentType transmission;

    @Column(name="decor")
    private ReplaceableComponentType decor;
}
