package ru.Burunsuzian.application.responses;

import java.time.Instant;
import java.util.UUID;

public record TestDriveResponse(UUID customerId, UUID carId, Instant startTime) { }
