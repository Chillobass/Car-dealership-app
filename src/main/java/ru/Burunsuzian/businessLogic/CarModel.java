package ru.Burunsuzian.businessLogic;

import lombok.NonNull;
import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
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
