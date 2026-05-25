package ru.burunsuzian.data.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burunsuzian.data.jpa.domainEntities.WarehouseAcceptanceEntity;

import java.util.Set;
import java.util.UUID;

@Repository
public interface WarehouseAcceptanceRepository extends JpaRepository<WarehouseAcceptanceEntity, UUID> {
    Set<WarehouseAcceptanceEntity> findAllByCarOrderId(UUID carOrderId);
}