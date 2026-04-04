package businessLogic.domain.carOrder;

import businessLogic.Car;
import businessLogic.domain.User;
import data.jpa.domainEntities.CarOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class CarOrder {
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