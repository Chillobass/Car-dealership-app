package org.example.application.responses;

import java.util.UUID;

public record CarOrderResponse(UUID carOrderId, UUID customerId, UUID managerId, UUID carId, String state) {}
