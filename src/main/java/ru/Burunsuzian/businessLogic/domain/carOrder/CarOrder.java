package ru.Burunsuzian.businessLogic.domain.carOrder;

import ru.Burunsuzian.businessLogic.Car;
import ru.Burunsuzian.businessLogic.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor @Getter @Setter
public class CarOrder {
    private UUID carOrderId;
    private User customer;
    private User manager;
    private Car orderedCar;
    private OrderState orderState;

    public void transitionTo(OrderState newState) {
        if (!orderState.canTransitionTo(newState)) {
            throw new IllegalStateException(
                    String.format("Невозможно перейти в %s из состояния %s", newState, orderState)
            );
        }
        orderState = newState;
    }
}