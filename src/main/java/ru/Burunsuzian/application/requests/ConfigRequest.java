package ru.Burunsuzian.application.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConfigRequest(@NotNull UUID carId, @NotNull UUID decor, @NotNull UUID steeringWheel, @NotNull UUID transmission, @NotNull UUID wheels) { }
