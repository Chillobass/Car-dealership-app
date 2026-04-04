package businessLogic.servicesImpl;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.domain.carOrder.CarOrder;
import businessLogic.domain.carOrder.CustomOrderState;
import businessLogic.domain.carOrder.DefaultOrderState;
import businessLogic.domain.carOrder.OrderState;
import businessLogic.exceptions.EntityNotFoundException;
import businessLogic.bdToEntityMappers.CarOrderMapper;
import data.persistence.repositories.CarOrderRepository;
import data.persistence.repositories.CarsRepository;
import data.persistence.services.CarOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarOrderServiceImpl implements CarOrderService {
    private CarOrderRepository carOrderRepository;
    private CarsRepository carsRepository;
    private CarOrderMapper carOrderMapper;

    @Override
    public void createOrder(User customer, User manager, Car orderedCar) {
        // 2 валидации: есть ли тачка в carRepo и нет ли уже ордера с этой тачкой
        if (!carsRepository.findAll().contains(orderedCar)){
            throw new EntityNotFoundException("no such car in repository");
        }
        boolean contains = carOrderRepository.findAll().contains(orderedCar.getCarID());

        if (contains) throw new EntityNotFoundException("this car is already ordered");

        CarOrder validCarOrder;
        if (orderedCar.isConfigured()) {
            validCarOrder = new CarOrder(customer, manager, orderedCar, CustomOrderState.PLACED);
        } else{
            validCarOrder = new CarOrder(customer, manager, orderedCar, DefaultOrderState.PLACED);
        }

        carOrderRepository.save(carOrderMapper.toEntity(validCarOrder));
    }

    @Override
    public void transitionTo(CarOrder carOrder, OrderState newState) {
        carOrder.transitionTo(newState);
    }
}
