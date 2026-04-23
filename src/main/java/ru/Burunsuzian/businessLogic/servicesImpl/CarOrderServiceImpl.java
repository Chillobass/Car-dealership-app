package ru.Burunsuzian.businessLogic.servicesImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.UUID;

import ru.Burunsuzian.application.responses.CarOrderResponse;
import ru.Burunsuzian.businessLogic.domain.carOrder.CarOrder;
import ru.Burunsuzian.businessLogic.domain.carOrder.DefaultOrderState;
import ru.Burunsuzian.businessLogic.domain.carOrder.OrderState;
import ru.Burunsuzian.businessLogic.domain.carOrder.CustomOrderStateToString;
import ru.Burunsuzian.businessLogic.domain.carOrder.DefaultOrderStateToString;
import ru.Burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.Burunsuzian.businessLogic.Mappers.CarMapper;
import ru.Burunsuzian.businessLogic.Mappers.CarOrderMapper;
import ru.Burunsuzian.businessLogic.Mappers.UserMapper;
import ru.Burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.Burunsuzian.data.jpa.domainEntities.CarOrderEntity;
import ru.Burunsuzian.data.jpa.domainEntities.UserEntity;
import ru.Burunsuzian.data.persistence.repositories.CarOrderRepository;
import ru.Burunsuzian.data.persistence.repositories.CarsRepository;
import ru.Burunsuzian.data.persistence.repositories.UserRepository;
import ru.Burunsuzian.data.persistence.services.CarOrderService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarOrderServiceImpl implements CarOrderService {
    private CarOrderRepository carOrderRepository;
    private CarsRepository carsRepository;
    private UserRepository userRepository;
    private CarOrderMapper carOrderMapper;
    private UserMapper userMapper;
    private CarMapper carMapper;

    @Override @Transactional
    public CarOrderResponse createOrder(UUID customer, UUID manager, UUID orderedCar) {

        CarEntity car = carsRepository.findById(orderedCar).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        UserEntity customerUser = userRepository.findById(customer).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        UserEntity managerUser = userRepository.findById(manager).orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        CarOrder validCarOrder = new CarOrder(UUID.randomUUID(),
                userMapper.toDomain(customerUser),
                userMapper.toDomain(managerUser),
                carMapper.toDomain(car),
                DefaultOrderState.PLACED);
        carOrderRepository.save(carOrderMapper.toEntity(validCarOrder));

        CarOrderResponse carOrderResponse = carOrderMapper.toDto(validCarOrder);

        return carOrderResponse;
    }

    @Override @Transactional
    public CarOrderResponse transitionTo(UUID carOrder, String newState) {
        CarOrderEntity carOrderEntity = carOrderRepository.findById(carOrder).orElseThrow(() -> new EntityNotFoundException("Car order not found"));
        CarOrder carOrderDomain = carOrderMapper.toDomain(carOrderEntity);

        boolean isCustomCar = carOrderDomain.getOrderedCar().isConfigured();
        OrderState newOrderState;

        if (isCustomCar) {
            var converter = new CustomOrderStateToString();
            newOrderState = converter.convert(newState);
        }
        else {
            var converter = new DefaultOrderStateToString();
            newOrderState = converter.convert(newState);
        }

        carOrderDomain.transitionTo(newOrderState);
        carOrderRepository.save(carOrderMapper.toEntity(carOrderDomain));

        return carOrderMapper.toDto(carOrderDomain);
    }

    @Override
    public CarOrderResponse getCarOrder(UUID carOrderId) {
        CarOrderEntity ent = carOrderRepository.findById(carOrderId).orElseThrow(() -> new EntityNotFoundException("no such entity"));
        CarOrderResponse response = new CarOrderResponse(
                ent.getId(), ent.getCustomerId(), ent.getManagerId(), ent.getOrderedCar(), ent.getOrderState().getState());
        return response;
    }

    @Override @Transactional
    public void deleteCarOrder(UUID carOrderId) {
        carOrderRepository.deleteById(carOrderId);
    }

    @Override @Transactional
    public void delete(UUID id) {
        carOrderRepository.deleteById(id);
    }
}
