package org.example;

import businessLogic.Car;
import businessLogic.CarModel;
import businessLogic.ConfigurationService;
import businessLogic.Context;
import businessLogic.details.replaceable.AutomaticTransmission;
import businessLogic.details.replaceable.ManualTransmission;
import businessLogic.details.replaceable.Replaceable;
import businessLogic.exceptions.IncompatibleComponentException;
import businessLogic.details.Detail;
import businessLogic.domain.valueObjects.Money;
import data.repositoriesImpl.CarOrderRepositoryImpl;
import data.repositoriesImpl.DetailRepositoryImpl;
import data.repositoriesImpl.CarsRepositoryImpl;
import data.repositoriesImpl.TestDriveRequestRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static businessLogic.details.irreplaceable.BodyType.SEDAN;
import static businessLogic.details.irreplaceable.BodyType.UNIVERSAL;
import static businessLogic.details.irreplaceable.DriveType.FRONT;
import static businessLogic.details.irreplaceable.FuelType.PETROL;
import static businessLogic.details.irreplaceable.GearBoxType.MECHANIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarCreationTest {
    @Test
    void test1(){
        CarModel carModel = CarModel.builder().brand("proudOfChina")
                .modelName("siDzinPin")
                .basePrice(new Money(new BigDecimal(70)))
                .carBody(SEDAN)
                .fuel(PETROL)
                .enginePower(100.0)
                .engineCapacity(50.0)
                .gearbox(MECHANIC)
                .carDrive(FRONT)
                .carColor("black")
                .build();

        Car car = Car.builder().carID(UUID.randomUUID()).model(carModel).build();

        ArrayList<CarModel> listik = new ArrayList<CarModel>();

        Detail detail = new Detail(UUID.randomUUID(),
                new ManualTransmission(), new Money(new BigDecimal(5)), listik);


        Map<Replaceable, Detail> forSwap = new HashMap<>();
        forSwap.put(detail.getCategory(), detail);

        CarsRepositoryImpl carsRep = new CarsRepositoryImpl();
        carsRep.add(car);

        DetailRepositoryImpl detRep = new DetailRepositoryImpl();
        detRep.add(detail);

        CarOrderRepositoryImpl carOrderRepository = new CarOrderRepositoryImpl();

        TestDriveRequestRepositoryImpl testDriveRequestRepository = new TestDriveRequestRepositoryImpl();

        Context context = new Context(carsRep, detRep, carOrderRepository, testDriveRequestRepository);

        var config = new ConfigurationService(context);


        assertThrows(IncompatibleComponentException.class, () -> config.createConfiguration(car, forSwap));

        assertEquals(true, carsRep.contains(car));
        assertEquals(true, detRep.contains(detail));
    }

    @Test
    void test2(){
        CarModel carModel = CarModel.builder()
                .brand("coolBrandYo")
                .modelName("solarius")
                .basePrice(new Money(new BigDecimal(70)))
                .carBody(UNIVERSAL)
                .fuel(PETROL)
                .enginePower(100.0)
                .engineCapacity(50.0)
                .gearbox(MECHANIC)
                .carDrive(FRONT)
                .carColor("silver")
                .build();

        ArrayList<CarModel> listik = new ArrayList<CarModel>();
        listik.add(carModel);

        var detalkaNaSwap = new Detail(UUID.randomUUID(), new ManualTransmission(), new Money(new BigDecimal(2)), listik);
        var config1 = new HashMap<Replaceable, Detail>();
        config1.put(new ManualTransmission(), detalkaNaSwap);

        Car car = Car.builder().carID(UUID.randomUUID()).model(carModel).config(config1).build();


        Detail detail = new Detail(UUID.randomUUID(),
                new AutomaticTransmission(), new Money(new BigDecimal(9)), listik);


        Map<Replaceable, Detail> forSwap = new HashMap<>();
        forSwap.put(detail.getCategory(), detail);

        CarsRepositoryImpl carsRep = new CarsRepositoryImpl();
        carsRep.add(car);

        DetailRepositoryImpl detRep = new DetailRepositoryImpl();
        detRep.add(detail);

        CarOrderRepositoryImpl carOrderRepository = new CarOrderRepositoryImpl();

        TestDriveRequestRepositoryImpl testDriveRequestRepository = new TestDriveRequestRepositoryImpl();

        Context context = new Context(carsRep, detRep, carOrderRepository, testDriveRequestRepository);

        var config = new ConfigurationService(context);
        car = config.createConfiguration(car, forSwap);


        assertEquals(true, car.getConfig().values().contains(detail));
    }
}