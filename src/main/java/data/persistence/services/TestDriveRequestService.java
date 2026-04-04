package data.persistence.services;

import businessLogic.Car;
import businessLogic.domain.User;

import java.sql.Timestamp;
import java.util.UUID;

public interface TestDriveRequestService {
    void createTestDriveRequest(User customer, Car orderedCar, Timestamp startTime);

    void markFinished(UUID order);
}
