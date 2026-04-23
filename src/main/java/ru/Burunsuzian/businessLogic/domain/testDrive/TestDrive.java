package ru.Burunsuzian.businessLogic.domain.testDrive;
import ru.Burunsuzian.businessLogic.Car;
import ru.Burunsuzian.businessLogic.domain.User;
import lombok.Getter;
import java.time.Instant;

@Getter
public class TestDrive {
    private User customer;
    private Car orderedCar;
    private Instant time;
    private boolean finished;

    public TestDrive(User currentCustomer, Car orderedForTest, Instant exactTime) {
        this.customer = currentCustomer;
        this.orderedCar = orderedForTest;
        this.time = exactTime;
        this.finished = false;
    }

    void Finish(){
        finished = true;
    }
}
