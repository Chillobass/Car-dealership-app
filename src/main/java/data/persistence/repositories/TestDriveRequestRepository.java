package data.persistence.repositories;

import businessLogic.domain.testDrive.TestDriveRequest;

public interface TestDriveRequestRepository {
    void Add(TestDriveRequest request);
}
