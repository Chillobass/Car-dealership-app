package ru.burunsuzian.data.persistence.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.burunsuzian.application.responses.WarehouseAcceptanceResponse;

import java.util.List;
import java.util.UUID;

public interface WarehouseAcceptanceService {
    UUID createWarehouseAcceptance(UUID carOrder, boolean isCustomCar);

    List<WarehouseAcceptanceResponse> getWarehouseAcceptance(UUID carOrder);

    List<WarehouseAcceptanceResponse> getAllWarehouseAcceptances();

    String updateWarehouseAcceptance(UUID warehouseAcceptanceId) throws JsonProcessingException;

    void deleteWarehouseAcceptance(UUID warehouseAcceptanceId);
}
