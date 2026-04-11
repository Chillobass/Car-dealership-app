package org.example.application.requests;

import java.util.UUID;

public record CarOrderCreateRequest(
        UUID customerId,
        UUID managerId,
        UUID carId,
        String state) {}

