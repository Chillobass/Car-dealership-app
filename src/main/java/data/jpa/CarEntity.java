package data.jpa;

import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name="cars")
public class CarEntity extends BaseJpaEntity {
    @Id
    @GeneratedValue
    @Column(name="car_id")
    private UUID carID;

    @ManyToOne @JoinColumn(name="model_name")
    @Column(name="car_model")
    private CarModelEntity model;

    private String wheels;
    private String transmission;
    private String decor;
    @Column(name="steering_wheels")
    private String steeringWheels;
}
