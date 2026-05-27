package ru.burunsuzian.application;

public record CarGrpcDto(
        String carId,
        String modelId,
        String brand,
        String color,
        String basePrice,
        boolean isCustomCar,
        boolean isAvailable
) {}
