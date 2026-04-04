package businessLogic.servicesImpl;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.exceptions.EntityNotFoundException;
import data.jpa.domainEntities.TestDriveRequestEntity;
import data.persistence.repositories.CarsRepository;
import data.persistence.repositories.TestDriveRequestRepository;
import data.persistence.services.TestDriveRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestDriveRequestServiceImpl implements TestDriveRequestService {
    private TestDriveRequestRepository testDriveRequestRepository;
    private CarsRepository carsRepository;

    @Override
    public void createTestDriveRequest(User customer, Car orderedCar, Timestamp startTime) {
        // 2 валидации: есть ли тачка в carRepo и нет ли уже тестДрайва с этой тачкой на это время
        if (!carsRepository.findAll().contains(orderedCar)){
            throw new EntityNotFoundException("no such car in repository");
        }

        List<TestDriveRequestEntity> ordersWithThisCar = testDriveRequestRepository.findAll().stream()
                .filter(testDriveRequest -> testDriveRequest.getId()==orderedCar.getCarID()).toList();

        if (!ordersWithThisCar.isEmpty()) {
            boolean doesntFit = ordersWithThisCar.stream().anyMatch(
                    order -> (startTime.toInstant().getEpochSecond() > order.getTime().toInstant().getEpochSecond()
                            && startTime.toInstant().getEpochSecond() < order.getTime().toInstant().getEpochSecond()+5400));
            if (doesntFit) throw new EntityNotFoundException("this car is already ordered");
        }
        testDriveRequestRepository.save(new TestDriveRequestEntity(customer.getUserID(), orderedCar.getCarID(), startTime, false));
    }

    @Override
    public void markFinished(UUID order) {
        TestDriveRequestEntity entity = testDriveRequestRepository.findById(order).orElseThrow(() -> new EntityNotFoundException("such TestDriveRequest is not found"));
        entity.setFinished(true);
    }
}
