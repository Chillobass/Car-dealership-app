package ru.burunsuzian.businessLogic.domain.testDrive;
import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

@Getter
public class TestDrive {
    private UUID customer;
    private UUID orderedCar;
    private Instant time;
    private boolean finished;

    public TestDrive(UUID currentCustomer, UUID orderedCar, Instant exactTime) {
        this.customer = currentCustomer;
        this.orderedCar = orderedCar;
        this.time = exactTime;
        this.finished = false;
    }

    void Finish(){
        finished = true;
    }
}
