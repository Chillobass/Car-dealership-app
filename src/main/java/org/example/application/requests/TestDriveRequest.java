package org.example.application.requests;

import java.time.Instant;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record TestDriveRequest(@NotNull UUID customerId, @NotNull UUID carId, @NotNull Instant time) { }
