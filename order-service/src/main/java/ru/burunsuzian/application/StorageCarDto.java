package ru.burunsuzian.application;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StorageCarDto(@NotNull UUID id, @NotNull boolean isCustomCar) {}
