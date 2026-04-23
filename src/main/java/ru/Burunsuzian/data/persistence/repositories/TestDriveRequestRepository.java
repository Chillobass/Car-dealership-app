package ru.Burunsuzian.data.persistence.repositories;

import ru.Burunsuzian.businessLogic.domain.testDrive.TestDrive;
import ru.Burunsuzian.data.jpa.domainEntities.TestDriveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

    @Repository
    public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequestEntity, UUID>, JpaSpecificationExecutor<TestDrive>{
    }
