package businessLogic;

import businessLogic.details.irreplaceable.BodyType;
import businessLogic.details.irreplaceable.DriveType;
import businessLogic.details.irreplaceable.FuelType;
import businessLogic.details.irreplaceable.GearBoxType;
import businessLogic.domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class CarModel {
    private String brand;
    private String modelName;
    private Money basePrice;

    private DriveType carDrive;
    private String carColor;
    private BodyType carBody;
    private FuelType fuel;
    private Double enginePower;
    private Double engineCapacity;
    private GearBoxType gearbox;
}