package ru.Burunsuzian.application.requests;

import java.math.BigDecimal;
import java.util.UUID;

public record CarCreationRequest (
    String brand,
    UUID modelId,
    BigDecimal basePrice,
    String carDrive,
    String carColor,
    String carBody,
    String fuel,
    Double enginePower,
    Double engineCapacity,
    String gearBox,

    UUID transmission,
    UUID steeringWheel,
    UUID wheels,
    UUID decor
){}
