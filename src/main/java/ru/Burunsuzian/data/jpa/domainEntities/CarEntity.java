package ru.Burunsuzian.data.jpa.domainEntities;

import ru.Burunsuzian.data.jpa.BaseJpaEntity;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="steering_wheel_id")
    private ReplaceableComponentType steeringWheel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="wheels_id")
    private ReplaceableComponentType wheels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transmission_id")
    private ReplaceableComponentType transmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="decor_id")
    private ReplaceableComponentType decor;
}
