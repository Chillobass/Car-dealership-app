package application.dto;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.domain.carOrder.OrderState;
import lombok.Getter;

public record CarOrderDto(User customer, User manager, Car orderedCar, OrderState orderState) { }
