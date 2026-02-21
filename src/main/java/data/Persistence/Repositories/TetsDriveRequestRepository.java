package data.Persistence.Repositories;

import data.Domain.TetsDriveRequest;

import java.util.List;

public interface TetsDriveRequestRepository {
    public void Add(TetsDriveRequest request);

//    public List<TetsDriveRequest> Query(); // че принимать
}
