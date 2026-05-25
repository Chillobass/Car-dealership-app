package ru.burunsuzian.application.requests;

import java.util.UUID;

public record CarOrderCreateRequest(
        UUID managerId,
        UUID carId,
        String state) {}

