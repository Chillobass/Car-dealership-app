package businessLogic.domain.testDrive;
import businessLogic.Car;
import businessLogic.domain.User;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class TestDriveRequest {
    // пусть любой TestDrive длится 1.5 часа, то бишь 5400 сек
    private User customer;
    private Car orderedCar;
    private Timestamp time; // TestDrive start time
    private Boolean finished;

    public TestDriveRequest(User currentCustomer, Car orderedForTest, Timestamp exactTime) {
        this.customer = currentCustomer;
        this.orderedCar = orderedForTest;
        this.time = exactTime;
        this.finished = false;
    }

    void Finish(){
        finished = true;
    }
}
