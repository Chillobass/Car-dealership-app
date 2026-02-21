package data.Persistence.Repositories;

import BusinessLogic.CarModel;

import java.util.List;
import java.util.UUID;

public interface ModelsRepository {
    CarModel add(CarModel carModel);

//    List<CarModel> query(CarFilter carFilter);

    void delete(UUID carID);
}
