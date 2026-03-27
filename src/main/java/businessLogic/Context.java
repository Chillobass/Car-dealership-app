package businessLogic;

import data.persistence.repositories.CarOrderRepository;
import data.persistence.repositories.DetailRepository;
import data.persistence.repositories.CarsRepository;
import data.persistence.repositories.TestDriveRequestRepository;
import data.repositoriesImpl.DetailRepositoryImpl;
import data.repositoriesImpl.CarsRepositoryImpl;
import businessLogic.domain.User;
import lombok.Getter;

@Getter
public class Context {
    private User user;
    private CarsRepository carsRepository;
    private DetailRepository detailRepository;
    private CarOrderRepository сarOrderRepository;
    private TestDriveRequestRepository tetsDriveRequestRepository;

    public Context(CarsRepositoryImpl modelsRepository, DetailRepositoryImpl detailRepository,
                   CarOrderRepository сarOrderRepository, TestDriveRequestRepository tetsDriveRequestRepository){
        this.carsRepository = modelsRepository;
        this.detailRepository = detailRepository;
        this.сarOrderRepository = сarOrderRepository;
        this.tetsDriveRequestRepository = tetsDriveRequestRepository;
    }
}
