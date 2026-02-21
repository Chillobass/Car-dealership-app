package BusinessLogic;

import data.RepositoriesImpl.DetailRepositoryImpl;
import data.RepositoriesImpl.ModelsRepositoryImpl;
import data.Domain.TetsDriveRequest;
import data.Domain.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Context {
    private User user;
    private ModelsRepositoryImpl modelsRepository;
    private DetailRepositoryImpl detailRepository;

    public Context(ModelsRepositoryImpl modelsRepository, DetailRepositoryImpl detailRepository){
        this.modelsRepository = modelsRepository;
        this.detailRepository = detailRepository;
    }
//    public void RequestTestDrive(CarModel currentCarModel, Timestamp testTime){ // спорно
//        TetsDriveRequest tetsDriveRequest = TetsDriveRequest.builder()
//                .customer(user)
//                .orderedCarModel(currentCarModel)
//                .time(testTime)
//                .build();
//
//    }
    // этот кал должен быть в сервисе
}
