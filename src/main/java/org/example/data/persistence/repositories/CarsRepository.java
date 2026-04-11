package org.example.data.persistence.repositories;

import org.example.businessLogic.Car;
import org.example.data.jpa.domainEntities.CarEntity;
import org.example.data.persistence.specifications.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarsRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {
}
