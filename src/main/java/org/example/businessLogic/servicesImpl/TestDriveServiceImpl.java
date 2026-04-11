package org.example.businessLogic.servicesImpl;

import org.example.application.responses.TestDriveResponse;
import org.example.businessLogic.Car;
import org.example.businessLogic.bdToEntityMappers.CarMapper;
import org.example.businessLogic.exceptions.EntityNotFoundException;
import org.example.data.jpa.domainEntities.CarEntity;
import org.example.data.jpa.domainEntities.TestDriveRequestEntity;
import org.example.data.persistence.repositories.CarsRepository;
import org.example.data.persistence.repositories.TestDriveRequestRepository;
import org.example.data.persistence.services.TestDriveService;
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
        // 2 валидации: есть ли тачка в carRepo и нет ли уже тестДрайва с этой тачкой на это время
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

    @Override
    public void deleteTestDrive(UUID request) {
        testDriveRequestRepository.deleteById(request);
    }
}
