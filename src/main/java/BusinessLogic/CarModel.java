package BusinessLogic;

import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;
import data.Domain.valueObjects.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter @AllArgsConstructor
public class CarModel {
    private UUID modelID;
    private String brand;
    private String modelName;
    private Money basePrice;

    private Map<DetailCategory, Detail> baseDetails;
}

/*
или же через UUID model хотим сходить в репу и достать с всё пресета

(Money price, String brand, UUID model, String carBody,
                  String fuel, Double enginePower, Double engineCapacity,
                  String gearbox, String carDrive, String carColour)

    private String carBody;
    private String fuel;
    private Double enginePower;
    private Double engineCapacity;
    private String gearbox;
    private String carDrive;
    private String carColour;

*/
