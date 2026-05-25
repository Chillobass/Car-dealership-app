package ru.burunsuzian.businessLogic.servicesImpl;

import org.springframework.web.client.RestTemplate;
import ru.burunsuzian.application.responses.TestDriveResponse;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.data.jpa.TestDriveRequestEntity;
import ru.burunsuzian.data.persistence.repositories.TestDriveRequestRepository;
import ru.burunsuzian.data.persistence.services.TestDriveService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestDriveServiceImpl implements TestDriveService {
    private final TestDriveRequestRepository testDriveRequestRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public TestDriveResponse createTestDriveRequest(UUID customerId, UUID carId, Instant startTime) {

        restTemplate.getForObject("http://storage-service:8082/cars/get/" + carId, UUID.class);

        List<TestDriveRequestEntity> ordersWithThisCar = testDriveRequestRepository.findAll().stream()
                .filter(testDriveRequest -> testDriveRequest.getId().equals(carId)).toList();

        if (!ordersWithThisCar.isEmpty()) {
            boolean doesntFit = ordersWithThisCar.stream().anyMatch(
                    order -> (startTime.getEpochSecond() > order.getTime().getEpochSecond()
                            && startTime.getEpochSecond() < order.getTime().getEpochSecond()+5400));
            if (doesntFit) throw new EntityNotFoundException("this car is already ordered");
        }
        testDriveRequestRepository.save(new TestDriveRequestEntity(customerId, carId, startTime, false));
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
