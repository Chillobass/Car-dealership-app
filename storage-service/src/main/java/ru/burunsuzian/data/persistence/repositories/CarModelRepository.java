package ru.burunsuzian.data.persistence.repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import ru.burunsuzian.data.jpa.domainEntities.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModelEntity, UUID> {}