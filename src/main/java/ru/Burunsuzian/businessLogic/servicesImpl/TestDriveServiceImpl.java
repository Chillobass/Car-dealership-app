package ru.Burunsuzian.businessLogic.servicesImpl;

import ru.Burunsuzian.application.responses.TestDriveResponse;
import ru.Burunsuzian.businessLogic.Car;
import ru.Burunsuzian.businessLogic.Mappers.CarMapper;
import ru.Burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.Burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.Burunsuzian.data.jpa.domainEntities.TestDriveRequestEntity;
import ru.Burunsuzian.data.persistence.repositories.CarsRepository;
import ru.Burunsuzian.data.persistence.repositories.TestDriveRequestRepository;
import ru.Burunsuzian.data.persistence.services.TestDriveService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor @RequiredArgsConstructor
public class TestDriveServiceImpl implements TestDriveService {
    private TestDriveRequestRepository testDriveRequestRepository;
    private CarsRepository carsRepository;
    private CarMapper carMapper;

    @Override
    @Transactional
    public TestDriveResponse createTestDriveRequest(UUID customerId, UUID carId, Instant startTime) {
        CarEntity orderedCarEntity = carsRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("no such car in our dealership"));
        Car orderedCar = carMapper.toDomain(orderedCarEntity);

        if (!carsRepository.findAll().contains(orderedCar)){
            throw new EntityNotFoundException("no such car in repository");
        }

        List<TestDriveRequestEntity> ordersWithThisCar = testDriveRequestRepository.findAll().stream()
                .filter(testDriveRequest -> testDriveRequest.getId()==orderedCar.getCarID()).toList();

        if (!ordersWithThisCar.isEmpty()) {
            boolean doesntFit = ordersWithThisCar.stream().anyMatch(
                    order -> (startTime.getEpochSecond() > order.getTime().getEpochSecond()
                            && startTime.getEpochSecond() < order.getTime().getEpochSecond()+5400));
            if (doesntFit) throw new EntityNotFoundException("this car is already ordered");
        }
        testDriveRequestRepository.save(new TestDriveRequestEntity(customerId, orderedCar.getCarID(), startTime, false));
        return new TestDriveResponse(customerId, carId, startTime);
    }

    @Override
    @Transactional
    public void markFinished(UUID request) {
        TestDriveRequestEntity entity = testDriveRequestRepository.findById(request).orElseThrow(() -> new EntityNotFoundException("such TestDriveRequest is not found"));
        entity.setFinished(true);
    }

    @Override
    public TestDriveResponse getTestDrive(UUID request) {
        TestDriveRequestEntity entity = testDriveRequestRepository.findById(request).orElseThrow(() -> new EntityNotFoundException("such TestDriveRequest is not found"));
        return new TestDriveResponse(entity.getId(), entity.getCustomerId(), entity.getTime());
    }

    @Override @Transactional
    public void deleteTestDrive(UUID request) {
        testDriveRequestRepository.deleteById(request);
    }
}
