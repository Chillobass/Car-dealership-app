package org.example.application.requests;

import java.util.UUID;

public record CarOrderRequest(
        UUID carOrderId,
        UUID customerId,
        UUID managerId,
        UUID carId,
        String state
) {}
