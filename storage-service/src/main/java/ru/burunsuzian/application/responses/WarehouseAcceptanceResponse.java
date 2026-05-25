package ru.burunsuzian.application.responses;

import java.util.Set;
import java.util.UUID;

public record WarehouseAcceptanceResponse(UUID id, UUID carOrderId, String orderType, UUID carId, UUID carModelId, Set<UUID> requiredComponentIds, UUID responsibleStaffId, String state) {
}
