package data.Persistence.Repositories;

import BusinessLogic.details.BodyType;
import BusinessLogic.details.DriveType;
import BusinessLogic.details.FuelType;
import BusinessLogic.details.TransmissionType;
import data.Domain.valueObjects.Money;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder @Getter
public class CarFilter {
    private Money minPrice;
    private Money maxPrice;
    private String brand;
    private String model;
    private BodyType bodyType;
    private FuelType fuelType;
    private Integer minEnginePower;
    private Integer maxEnginePower;
    private Double minEngineVolume;
    private Double maxEngineVolume;
    private TransmissionType transmission;
    private DriveType drive;
    private String color;
}
