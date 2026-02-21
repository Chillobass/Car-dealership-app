package BusinessLogic;

import BusinessLogic.details.*;
import data.Domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter @Builder
public class Car {
    private CarModel model;
    private UUID carID;
    private Money finalPrice;
    private BodyType carBody;
    private FuelType fuel;
    private Double enginePower;
    private Double engineCapacity;
    private GearBoxType gearbox;
    private DriveType carDrive;
    private String carColour;
    private TransmissionType transmission;

}
