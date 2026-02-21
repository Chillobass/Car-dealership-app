package data.RepositoriesImpl;

import data.Domain.CarOrder;
import data.Domain.OrderStatus;
import data.Persistence.Repositories.CarOrderRepository;

import java.util.HashMap;
import java.util.List;

import static data.Domain.OrderStatus.BOOKED;

public final class CarOrderRepositoryImpl implements CarOrderRepository {
    private final HashMap<CarOrder, OrderStatus> storage = new HashMap<>();

    @Override
    public void Add(CarOrder carOrder) {
        storage.put(carOrder, BOOKED);
    }

//    @Override
//    public List<CarOrder> Query() {
//    }
}
