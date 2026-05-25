package ru.burunsuzian.application.events;

import java.util.UUID;

public record WarehouseApprovalResponse(UUID carOrderId, String traceId, boolean isApproved) {
}
