package org.example.businessLogic.domain.testDrive;
import org.example.businessLogic.Car;
import org.example.businessLogic.domain.User;
import lombok.Getter;
import java.time.Instant;

@Getter
public class TestDrive {
    // пусть любой TestDrive длится 1.5 часа, то бишь 5400 сек
    private User customer;
    private Car orderedCar;
    private Instant time; // TestDrive start time
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
