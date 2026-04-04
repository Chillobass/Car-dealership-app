package data.persistence.services;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.domain.carOrder.CarOrder;
import businessLogic.domain.carOrder.OrderState;

import java.util.UUID;

public interface CarOrderService {
    void createOrder(User customer, User manager, Car orderedCar);

    void transitionTo(CarOrder carOrder, OrderState newState);
}
