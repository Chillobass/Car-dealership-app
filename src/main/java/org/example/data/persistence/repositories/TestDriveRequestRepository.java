package org.example.data.persistence.repositories;

import org.example.businessLogic.domain.testDrive.TestDrive;
import org.example.data.jpa.domainEntities.TestDriveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

    @Repository
    public interface TestDriveRequestRepository extends JpaRepository<TestDriveRequestEntity, UUID>, JpaSpecificationExecutor<TestDrive>{
    }
