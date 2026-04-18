package ru.Burunsuzian.data.persistence.repositories;

import ru.Burunsuzian.data.jpa.domainEntities.CarOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarOrderRepository extends JpaRepository<CarOrderEntity, UUID>, JpaSpecificationExecutor<CarOrderEntity> {
}