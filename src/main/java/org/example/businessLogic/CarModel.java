package org.example.businessLogic;

import lombok.NonNull;
import org.example.businessLogic.details.irreplaceable.Irreplaceable;
import org.example.businessLogic.details.irreplaceable.bodyType.BodyType;
import org.example.businessLogic.details.irreplaceable.driveType.DriveType;
import org.example.businessLogic.details.irreplaceable.fuelType.FuelType;
import org.example.businessLogic.details.irreplaceable.gearBoxType.GearBoxType;
import org.example.businessLogic.domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter @AllArgsConstructor @Builder
public class CarModel {
    private @NonNull String brand;
    private @NonNull UUID modelId;
    private @NonNull Money basePrice;

    private @NonNull Irreplaceable carDrive;
    private @NonNull String carColor;
    private @NonNull Irreplaceable carBody;
    private @NonNull Irreplaceable fuel;
    private @NonNull Double enginePower;
    private @NonNull Double engineCapacity;
    private @NonNull Irreplaceable gearBox;
}
