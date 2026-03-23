package businessLogic.domain.carOrder;

import businessLogic.Car;
import businessLogic.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CarOrder {
    private User customer;
    private User manager;
    private Car orderedCar;
    @Getter
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