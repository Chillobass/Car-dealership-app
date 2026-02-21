package data.RepositoriesImpl;

import BusinessLogic.CarModel;
import data.Persistence.Repositories.ModelsRepository;

import java.util.HashMap;
import java.util.UUID;

public class ModelsRepositoryImpl implements ModelsRepository {
    private final HashMap<UUID, CarModel> storage = new HashMap<>(); // final ли ??

    @Override
    public CarModel add(CarModel carModel) {
        storage.put(carModel.getModelID(), carModel);
        return carModel;
    }

    public boolean contains(CarModel carModel){
        return storage.containsValue(carModel);
    }

//    @Override
//    public List<CarModel> query(CarFilter filter) {
//    }

    @Override
    public void delete(UUID carID){
        storage.remove(carID);
    }

}
