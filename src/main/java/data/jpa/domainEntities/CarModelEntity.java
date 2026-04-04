package data.jpa.domainEntities;

import data.jpa.BaseJpaEntity;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity @Table(name="car_models")
@AttributeOverride(name = "id", column = @Column(name = "model_id"))
public class CarModelEntity extends BaseJpaEntity {
    private String brand;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "car_color")
    private String carColor;

    @ManyToOne
    @JoinColumn(name = "car_drive")
    private IrreplaceableComponentType carDrive;

    @ManyToOne
    @JoinColumn(name = "car_body")
    private IrreplaceableComponentType carBody;

    @ManyToOne
    @JoinColumn(name = "fuel")
    private IrreplaceableComponentType fuel;

    @ManyToOne
    @JoinColumn(name = "gear_box")
    private IrreplaceableComponentType gearBox;


    @Column(name = "engine_power")
    private Double enginePower;

    @Column(name = "engine_capacity")
    private Double engineCapacity;

}
