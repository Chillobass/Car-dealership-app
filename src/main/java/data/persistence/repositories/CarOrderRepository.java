package data.persistence.repositories;

import businessLogic.domain.carOrder.CarOrder;

public interface CarOrderRepository {
    public void Add(CarOrder carOrder);
}