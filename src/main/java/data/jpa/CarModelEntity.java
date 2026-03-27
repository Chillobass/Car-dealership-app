package data.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name="car_models")
public class CarModelEntity extends BaseJpaEntity {
    @Id
    @Column(name = "model_name")
    private String modelName;

    private String brand;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "car_drive")
    private String carDrive;

    @Column(name = "car_color")
    private String carColor;

    @Column(name = "car_body")
    private String carBody;

    private String fuel;

    @Column(name = "engine_power")
    private Double enginePower;

    @Column(name = "engine_capacity")
    private Double engineCapacity;

    private String gearbox;
}
