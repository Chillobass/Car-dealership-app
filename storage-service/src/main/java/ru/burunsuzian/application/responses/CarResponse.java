package ru.burunsuzian.application.responses;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarResponse(@NotNull UUID carId) {
}