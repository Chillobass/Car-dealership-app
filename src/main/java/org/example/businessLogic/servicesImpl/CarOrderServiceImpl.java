package org.example.businessLogic.servicesImpl;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.example.application.responses.CarOrderResponse;
import org.example.businessLogic.domain.carOrder.CarOrder;
import org.example.businessLogic.domain.carOrder.DefaultOrderState;
import org.example.businessLogic.domain.carOrder.OrderState;
import org.example.businessLogic.domain.carOrder.CustomOrderStateToString;
import org.example.businessLogic.domain.carOrder.DefaultOrderStateToString;
import org.example.businessLogic.exceptions.EntityNotFoundException;
import org.example.businessLogic.bdToEntityMappers.CarMapper;
import org.example.businessLogic.bdToEntityMappers.CarOrderMapper;
import org.example.businessLogic.bdToEntityMappers.UserMapper;
import org.example.data.jpa.domainEntities.CarEntity;
import org.example.data.jpa.domainEntities.CarOrderEntity;
import org.example.data.jpa.domainEntities.UserEntity;
import org.example.data.persistence.repositories.CarOrderRepository;
import org.example.data.persistence.repositories.CarsRepository;
import org.example.data.persistence.repositories.UserRepository;
import org.example.data.persistence.services.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<CarOrderResponse> createOrder(UUID customer, UUID manager, UUID orderedCar) {

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

        return new ResponseEntity<CarOrderResponse>(carOrderResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CarOrderResponse> transitionTo(UUID carOrder, String newState) {
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
        CarOrderResponse carOrderResponse = carOrderMapper.toDto(carOrderDomain);
        return new ResponseEntity<CarOrderResponse>(carOrderResponse, HttpStatus.OK);
    }

    @Override
    public void delete(UUID id) {
        carOrderRepository.deleteById(id);
    }
}
