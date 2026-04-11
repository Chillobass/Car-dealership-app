package org.example.IntegrationalTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.example.businessLogic.Car;
import org.example.businessLogic.domain.carOrder.CarOrder;
import org.example.data.persistence.repositories.CarOrderRepository;
import org.example.data.persistence.repositories.CarsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CarOrderRepositoryIT extends BaseIntegrationTest {
    @Autowired
    private CarOrderRepository orderRepository;

    @Autowired
    private CarsRepository carRepository;


    @Test
    void shouldAddAndReturnOrder() {
        // arrange
        Car car = Car.create(new Price(10), "m340i", UUID.randomUUID());
        CarOrder order = new CarOrder(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), car.getId());

        // act
        carRepository.add(car);
        orderRepository.add(order);

        var found = orderRepository.getById(order.getId());

        // assert
        assertTrue(found.isPresent());
        assertEquals(found.get().getCar(), order.getCar());
        assertEquals(found.get().getState(), order.getState());
    }
}
