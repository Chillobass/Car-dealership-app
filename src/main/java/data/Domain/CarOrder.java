package data.Domain;

import BusinessLogic.CarModel;
import lombok.Builder;

@Builder
public class CarOrder {
    private User customer;
    private User manager; // надо будет случайно выбрать и передать в кнстртр
    private CarModel orderedCarModel;

    public CarOrder(User broWhoOrdered, User currentManager, CarModel whatBroOrdered){
        customer = broWhoOrdered;
        manager = currentManager;
        orderedCarModel = whatBroOrdered;
    }
}
