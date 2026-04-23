package ru.Burunsuzian;

import java.util.HashMap;
import java.util.UUID;

import jakarta.transaction.Transactional;
import ru.Burunsuzian.businessLogic.Car;
import ru.Burunsuzian.businessLogic.CarModel;
import ru.Burunsuzian.businessLogic.Mappers.CarMapper;
import ru.Burunsuzian.businessLogic.Mappers.CarModelMapper;
import ru.Burunsuzian.businessLogic.Mappers.UserMapper;
import ru.Burunsuzian.businessLogic.details.Detail;
import ru.Burunsuzian.businessLogic.details.irreplaceable.bodyType.CoupeBody;
import ru.Burunsuzian.businessLogic.details.irreplaceable.driveType.FrontDrive;
import ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.Burunsuzian.businessLogic.details.irreplaceable.gearBoxType.MechanicGearBox;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.domain.User;
import ru.Burunsuzian.businessLogic.domain.UserType;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.data.jpa.domainEntities.CarOrderEntity;
import ru.Burunsuzian.data.jpa.domainEntities.DefaultOrderStateEntity;
import ru.Burunsuzian.data.jpa.domainEntities.UserEntity;
import ru.Burunsuzian.data.persistence.repositories.CarModelRepository;
import ru.Burunsuzian.data.persistence.repositories.CarOrderRepository;
import ru.Burunsuzian.data.persistence.repositories.CarsRepository;
import ru.Burunsuzian.data.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarOrderRepositoryIT extends BaseIntegrationTest {

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarModelMapper carModelMapper;

    @Autowired
    private CarOrderRepository orderRepository;

    @Autowired
    private CarsRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test @Transactional
    void shouldAddAndReturnOrder() {
        // arrange
        var carModel = CarModel.builder()
                .brand("abibas")
                .modelId(UUID.randomUUID())
                .basePrice(new Money(123))
                .carDrive(new FrontDrive())
                .carColor("Black")
                .carBody(new CoupeBody())
                .fuel(new PetrolFuel())
                .enginePower(12.0)
                .engineCapacity(20.0)
                .gearBox(new MechanicGearBox()).build();

        carModelRepository.save(carModelMapper.toEntity(carModel));

        HashMap<Replaceable, Detail> configMapa = new HashMap<>();
        Car car = Car.builder().carID(UUID.randomUUID()).model(carModel).configured(false).config(configMapa).build();

        User customer = new User(UserType.CLIENT);
        UserEntity customerEntity = userMapper.toEntity(customer);
        UUID customerId = userRepository.save(customerEntity).getId();

        User manager = new User(UserType.LOCAL_MANAGER);
        UserEntity managerEntity = userMapper.toEntity(manager);
        UUID managerId = userRepository.save(managerEntity).getId();

        CarOrderEntity order = new CarOrderEntity(customerId, managerId, car.getCarID(), new DefaultOrderStateEntity("PLACED"));

        // act
        carRepository.save(carMapper.toEntity(car));
        orderRepository.save(order);

        var found = orderRepository.getById(order.getId());

        // assert
        assertEquals(found.getOrderedCar(), order.getOrderedCar());
        assertEquals(found.getOrderState().getState(), order.getOrderState().getState());
    }
}
