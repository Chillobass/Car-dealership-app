package data.persistence.repositories;

import businessLogic.Car;
import data.jpa.domainEntities.CarEntity;
import data.persistence.specifications.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarsRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {
    List<Car> query(Specification<Car> specification);
}
