package ru.burunsuzian.data.jpa.domainEntities;

import lombok.*;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import ru.burunsuzian.data.jpa.BaseJpaEntity;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Table(name="cars")
@AttributeOverride(name = "id", column = @Column(name = "car_id"))
public class CarEntity extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="model_name")
    private CarModelEntity model;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="steering_wheel_id")
    private ReplaceableComponentType steeringWheel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="wheels_id")
    private ReplaceableComponentType wheels;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="transmission_id")
    private ReplaceableComponentType transmission;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="decor_id")
    private ReplaceableComponentType decor;

    @Builder.Default
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;
}
