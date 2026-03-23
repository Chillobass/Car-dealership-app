package data.repositoriesImpl;

import businessLogic.domain.carOrder.CarOrder;
import data.persistence.repositories.CarOrderRepository;

import java.util.ArrayList;
import java.util.List;

public final class CarOrderRepositoryImpl implements CarOrderRepository {
    private final List<CarOrder> storage = new ArrayList<CarOrder>();

    @Override
    public void Add(CarOrder carOrder) {
        storage.add(carOrder);
    }
}
