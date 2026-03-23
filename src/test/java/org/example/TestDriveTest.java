package org.example;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.domain.User;
import businessLogic.domain.UserType;
import businessLogic.domain.testDrive.TestDriveRequest;
import businessLogic.domain.valueObjects.Money;
import data.repositoriesImpl.TestDriveRequestRepositoryImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static businessLogic.details.irreplaceable.BodyType.UNIVERSAL;
import static businessLogic.details.irreplaceable.DriveType.FRONT;
import static businessLogic.details.irreplaceable.FuelType.PETROL;
import static businessLogic.details.irreplaceable.GearBoxType.MECHANIC;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDriveTest {
    @Test
    void test4(){
        CarModel carModel = CarModel.builder()
                .brand("anotherBrand")
                .modelName("lehus")
                .basePrice(new Money(new BigDecimal(70)))
                .carBody(UNIVERSAL)
                .fuel(PETROL)
                .enginePower(100.0)
                .engineCapacity(50.0)
                .gearbox(MECHANIC)
                .carDrive(FRONT)
                .carColor("oldMoney")
                .build();

        Car car = Car.builder().carID(UUID.randomUUID()).model(carModel).config(new HashMap<>()).build();

        var ussr = new User(UserType.client);
        TestDriveRequest request1 = new TestDriveRequest(ussr,
                car, new Timestamp(Instant.now().getEpochSecond()));

        TestDriveRequest request2 = new TestDriveRequest(ussr,
                car, new Timestamp(Instant.now().getEpochSecond()));

        TestDriveRequestRepositoryImpl testDriveRequestRepository = new TestDriveRequestRepositoryImpl();
        testDriveRequestRepository.Add(request1);

        assertThrows(RuntimeException.class, () -> testDriveRequestRepository.Add(request2));
    }
}