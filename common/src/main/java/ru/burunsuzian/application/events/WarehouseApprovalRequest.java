package ru.burunsuzian.application.events;

import java.util.UUID;

public record WarehouseApprovalRequest(UUID carOrderId, String traceId, boolean isCustomCar) {
}
