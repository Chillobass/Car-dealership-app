package ru.burunsuzian.data.persistence.repositories;

import ru.burunsuzian.data.jpa.TestDriveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

    @Repository
    public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequestEntity, UUID>, JpaSpecificationExecutor<TestDriveRequestEntity>{
    }
