package ru.Burunsuzian.data.persistence.services;

import ru.Burunsuzian.application.responses.DetailResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface DetailManagementService {
    DetailResponse add(BigDecimal price, String category);

    void remove(UUID detail);

    void addCompatibleModel(UUID detailId, UUID carModelId);

    String getDetail(UUID detailId);
}
