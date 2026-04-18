package org.example.data.persistence.services;

import org.example.application.requests.DetailRequest;
import org.example.application.responses.DetailResponse;
import org.example.businessLogic.details.Detail;
import org.example.businessLogic.details.replaceable.Replaceable;

import java.math.BigDecimal;
import java.util.UUID;

public interface DetailManagementService {
    DetailResponse add(BigDecimal price, String category);

    void remove(UUID detail);

    void addCompatibleModel(UUID detailId, UUID carModelId);

    String getDetail(UUID detailId);
}
