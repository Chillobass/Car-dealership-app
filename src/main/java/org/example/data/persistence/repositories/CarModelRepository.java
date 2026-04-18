package org.example.data.persistence.repositories;

import java.util.UUID;

import org.example.data.jpa.domainEntities.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModelEntity, UUID> {}