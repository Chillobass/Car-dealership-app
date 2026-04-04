package businessLogic;

import businessLogic.details.irreplaceable.bodyType.BodyType;
import businessLogic.details.irreplaceable.driveType.DriveType;
import businessLogic.details.irreplaceable.fuelType.FuelType;
import businessLogic.details.irreplaceable.gearBoxType.GearBoxType;
import businessLogic.domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter @AllArgsConstructor @Builder
public class CarModel {
    private String brand;
    private UUID modelId;
    private Money basePrice;

    private DriveType carDrive;
    private String carColor;
    private BodyType carBody;
    private FuelType fuel;
    private Double enginePower;
    private Double engineCapacity;
    private GearBoxType gearBox;
}
