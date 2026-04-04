package data.persistence.services;

import businessLogic.details.Detail;

import java.util.UUID;

public interface DetailManagementService {
    void add(Detail detail);

    void remove(UUID detail);
}
