package data.Persistence.Repositories;

import data.Domain.CarOrder;

import java.util.List;

public interface CarOrderRepository {
    public void Add(CarOrder carOrder);

//    public List<CarOrder> Query();
}