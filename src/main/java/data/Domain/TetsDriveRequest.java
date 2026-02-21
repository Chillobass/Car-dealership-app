package data.Domain;

import BusinessLogic.CarModel;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class TetsDriveRequest {
    private User customer;
    private CarModel orderedCarModel;
    private Timestamp time;
    // add id ?

    public TetsDriveRequest(User currentCustomer, CarModel orderedForTest, Timestamp exactTime){
        customer = currentCustomer;
        orderedCarModel = orderedForTest;
        time = exactTime;
    }
}
