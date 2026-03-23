package data.repositoriesImpl;

import businessLogic.domain.testDrive.TestDriveRequest;
import data.persistence.repositories.TestDriveRequestRepository;

import java.util.ArrayList;
import java.util.List;

public class TestDriveRequestRepositoryImpl implements TestDriveRequestRepository {
    private List<TestDriveRequest> repository = new ArrayList<>();

    @Override
    public void Add(TestDriveRequest request){
        var requested = request.getOrderedCar().getCarID();
        final int timeForTestDrive = 5400;
        for (var i : repository){
            if ((i.getOrderedCar().getCarID() == requested)
                    && (i.getTime().toInstant().getEpochSecond()+timeForTestDrive > request.getTime().toInstant().getEpochSecond())){
                throw new RuntimeException("this car os buisy for this time");
            }
        }
        repository.add(request);
    }
}
