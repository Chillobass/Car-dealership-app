package org.example;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.domain.User;
import businessLogic.domain.UserType;
import businessLogic.domain.carOrder.CarOrder;
import businessLogic.domain.carOrder.DefaultOrderState;
import businessLogic.domain.valueObjects.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static businessLogic.details.irreplaceable.BodyType.UNIVERSAL;
import static businessLogic.details.irreplaceable.DriveType.FRONT;
import static businessLogic.details.irreplaceable.FuelType.PETROL;
import static businessLogic.details.irreplaceable.GearBoxType.MECHANIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OrderTests {
    @Test
    void test3(){
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

        System.out.println("isEnum: " + DefaultOrderState.class.isEnum());
        var carOrder = new CarOrder(new User(UserType.client), new User(UserType.localManager), car, DefaultOrderState.PLACED);

        carOrder.transitionTo(DefaultOrderState.MANAGER_APPROVED);

        assertEquals(DefaultOrderState.MANAGER_APPROVED, carOrder.getOrderState());
        assertThrows(IllegalStateException.class, () -> carOrder.transitionTo(DefaultOrderState.COMPLETED));
    }
}