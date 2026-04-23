package ru.Burunsuzian.data.persistence.repositories;

import java.util.UUID;

import ru.Burunsuzian.data.jpa.domainEntities.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModelEntity, UUID> {}