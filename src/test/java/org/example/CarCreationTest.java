package org.example;

import BusinessLogic.Car;
import BusinessLogic.CarModel;
import BusinessLogic.ConfigurationService;
import BusinessLogic.Context;
import BusinessLogic.Exceptions.IncompatibleComponentException;
import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;
import data.Domain.valueObjects.Money;
import data.RepositoriesImpl.DetailRepositoryImpl;
import data.RepositoriesImpl.ModelsRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static BusinessLogic.details.BodyType.SEDAN;
import static BusinessLogic.details.DriveType.FRONT;
import static BusinessLogic.details.FuelType.PETROL;
import static BusinessLogic.details.GearBoxType.MECHANIC;
import static BusinessLogic.details.TransmissionType.MANUAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarCreationTest {
    @Test
    void test1(){
        CarModel carModel = new CarModel(UUID.randomUUID(), "Merc", "a80",
                new Money(new BigDecimal(90)), new HashMap<>());

        ArrayList<CarModel> listik = new ArrayList<CarModel>();
        Detail detail = new Detail(UUID.randomUUID(), new DetailCategory("cat1", UUID.randomUUID()), "det1",
                new Money(new BigDecimal(4)), listik);



        Map<DetailCategory, Detail> forSwap = new HashMap<>();
        forSwap.put(detail.getCategory(), detail);



        ModelsRepositoryImpl modRep = new ModelsRepositoryImpl();
        modRep.add(carModel);

        DetailRepositoryImpl detRep = new DetailRepositoryImpl();
        detRep.add(detail);

        Context context = new Context(modRep, detRep);

        ConfigurationService config = new ConfigurationService(context);

        Car car = Car.builder()
                .model(carModel).
                carID(UUID.randomUUID()).
                finalPrice(new Money(new BigDecimal(70)))
                .carBody(SEDAN)
                .fuel(PETROL)
                .enginePower(100.0)
                .engineCapacity(50.0)
                .gearbox(MECHANIC)
                .carDrive(FRONT)
                .carColour("black")
                .transmission(MANUAL).build();

        assertThrows(IncompatibleComponentException.class, () -> config.createConfiguration(car, forSwap));

        assertEquals(modRep.contains(carModel), true);
        assertEquals(detRep.contains(detail), true);
    }

    @Test
    void test2(){
        CarModel carModel = new CarModel(UUID.randomUUID(), "bmw", "bigBmw",
                new Money(new BigDecimal(90)), new HashMap<>());

        ArrayList<CarModel> listik = new ArrayList<CarModel>();
        listik.add(carModel);
        Detail detail = new Detail(UUID.randomUUID(), new DetailCategory("cat1", UUID.randomUUID()), "det1",
                new Money(new BigDecimal(4)), listik);


        ModelsRepositoryImpl modRep = new ModelsRepositoryImpl();
        modRep.add(carModel);

        DetailRepositoryImpl detRep = new DetailRepositoryImpl();
        detRep.add(detail);

        Context context = new Context(modRep, detRep);

        ConfigurationService config = new ConfigurationService(context);

        Car car = Car.builder()
                .model(carModel).
                carID(UUID.randomUUID()).
                finalPrice(new Money(new BigDecimal(70)))
                .carBody(SEDAN)
                .fuel(PETROL)
                .enginePower(100.0)
                .engineCapacity(50.0)
                .gearbox(MECHANIC)
                .carDrive(FRONT)
                .carColour("black")
                .transmission(MANUAL).build();

        Map<DetailCategory, Detail> swaps = new HashMap<>();
        swaps.put(detail.getCategory(), detail);
        config.createConfiguration(car, swaps);

        assertEquals(modRep.contains(carModel), true);
        assertEquals(detRep.contains(detail), true);
    }

}
