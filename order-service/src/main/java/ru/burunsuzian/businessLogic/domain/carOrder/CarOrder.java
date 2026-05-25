package ru.burunsuzian.businessLogic.domain.carOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor @Getter @Setter
public class CarOrder {
    private UUID carOrderId;
    private UUID customerId;
    private UUID managerId;
    private UUID carId;
    private boolean isCustomCar;
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