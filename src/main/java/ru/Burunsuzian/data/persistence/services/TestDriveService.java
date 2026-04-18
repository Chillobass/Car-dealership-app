package ru.Burunsuzian.data.persistence.services;

import ru.Burunsuzian.application.responses.TestDriveResponse;

import java.time.Instant;
import java.util.UUID;

public interface TestDriveService {
    TestDriveResponse createTestDriveRequest(UUID customerId, UUID carId, Instant startTime);

    void markFinished(UUID order);

    TestDriveResponse getTestDrive(UUID request);

    void deleteTestDrive(UUID request);
}
