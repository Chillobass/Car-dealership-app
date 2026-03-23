package data.jpa;

import businessLogic.details.irreplaceable.BodyType;
import businessLogic.details.irreplaceable.DriveType;
import businessLogic.details.irreplaceable.FuelType;
import businessLogic.details.irreplaceable.GearBoxType;
import businessLogic.domain.valueObjects.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class CarModelEntity extends BaseJpaEntity {
    @Id
    @Column(name = "model_id")
    private String modelId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "base_price")
    private Money basePrice;

    @Column(name = "car_drive")
    private DriveType carDrive;

    @Column(name = "car_color")
    private String carColor;

    @Column(name = "car_body")
    private BodyType carBody;

    @Column(name = "fuel")
    private FuelType fuel;

    @Column(name = "engine_power")
    private Double enginePower;

    @Column(name = "engine_capacity")
    private Double engineCapacity;

    @Column(name = "gearbox")
    private GearBoxType gearbox;
}
