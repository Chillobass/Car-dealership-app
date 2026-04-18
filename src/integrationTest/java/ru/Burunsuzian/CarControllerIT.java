package ru.Burunsuzian;

import jakarta.transaction.Transactional;

import ru.Burunsuzian.businessLogic.Car;
import ru.Burunsuzian.businessLogic.CarModel;
import ru.Burunsuzian.businessLogic.details.Detail;
import ru.Burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.Burunsuzian.businessLogic.details.irreplaceable.bodyType.SedanBody;
import ru.Burunsuzian.businessLogic.details.irreplaceable.driveType.FullDrive;
import ru.Burunsuzian.businessLogic.details.irreplaceable.fuelType.PetrolFuel;
import ru.Burunsuzian.businessLogic.details.irreplaceable.gearBoxType.AutomaticGearBox;
import ru.Burunsuzian.businessLogic.details.replaceable.BestDecor;
import ru.Burunsuzian.businessLogic.details.replaceable.ManualTransmission;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.details.replaceable.HotWheels;
import ru.Burunsuzian.businessLogic.details.replaceable.RoundSteeringWheel;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.Burunsuzian.businessLogic.Mappers.CarMapper;
import ru.Burunsuzian.businessLogic.Mappers.CarModelMapper;
import ru.Burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.Burunsuzian.data.jpa.domainEntities.CarModelEntity;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.Burunsuzian.data.jpa.replaceableEntities.BestDecorEntity;
import ru.Burunsuzian.data.persistence.repositories.CarModelRepository;
import ru.Burunsuzian.data.persistence.repositories.CarsRepository;
import ru.Burunsuzian.data.persistence.repositories.DetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarControllerIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private CarsRepository carRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarModelMapper carModelMapper;

    @Autowired
    private CarMapper carMapper;

    @SuppressWarnings("null")
    @Test
    void shouldCreateDetailViaApi() throws Exception {
        // arrange
        ReplaceableComponentType detail = new BestDecorEntity();
        detailRepository.save(detail);

        String requestJson = """
                {
                    "price": "5",
                    "category": "BestDecor"
                }
                """.formatted(detail.getId());

        // 2. act & assert
        mockMvc.perform(post("/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @SuppressWarnings("null")
    @Test
    void shouldGetCarByIdViaApi() throws Exception {
        // arrange
        Irreplaceable carBody = new SedanBody();
        Irreplaceable carDrive = new FullDrive();
        Irreplaceable fuel = new PetrolFuel();
        Irreplaceable gearBox = new AutomaticGearBox();

        UUID carModelId = UUID.randomUUID();
        CarModel carModel = CarModel.builder()
                .basePrice(new Money(500))
                .modelId(carModelId)
                .brand("Mozhno a zachem?")
                .carBody(carBody)
                .carColor("black")
                .carDrive(carDrive)
                .engineCapacity(10.0)
                .enginePower(1000.0)
                .fuel(fuel)
                .gearBox(gearBox)
                .build();

        CarModelEntity carModelEntity = carModelMapper.toEntity(carModel);
        carModelRepository.save(carModelEntity);

        Detail decor = new Detail(
                UUID.randomUUID(),
                new BestDecor(),
                new Money(5),
                new HashSet<UUID>(){{
                    add(carModelId);
                }}
        );

        Detail transmission = new Detail(
                UUID.randomUUID(),
                new ManualTransmission(),
                new Money(10),
                new HashSet<UUID>(){{
                    add(carModelId);
                }}
        );

        Detail steeringWheel = new Detail(
                UUID.randomUUID(),
                new RoundSteeringWheel(),
                new Money(15),
                new HashSet<UUID>(){{
                    add(carModelId);
                }}
        );

        Detail wheels = new Detail(
                UUID.randomUUID(),
                new HotWheels(),
                new Money(20),
                new HashSet<UUID>(){{
                    add(carModelId);
                }}
        );

        detailRepository.saveAll(Set.of(
                decor.getCategory().toEntity(decor.getPrice(), decor.getCompatibleModels()),
                transmission.getCategory().toEntity(transmission.getPrice(), transmission.getCompatibleModels()),
                steeringWheel.getCategory().toEntity(steeringWheel.getPrice(), steeringWheel.getCompatibleModels()),
                wheels.getCategory().toEntity(wheels.getPrice(), wheels.getCompatibleModels())
        ));

        HashMap <Replaceable, Detail> cfg = new HashMap<Replaceable, Detail>() {{
            put(decor.getCategory(), decor);
            put(transmission.getCategory(), transmission);
            put(steeringWheel.getCategory(), steeringWheel);
            put(wheels.getCategory(), wheels);
        }};

        Car car = Car.builder()
                .model(carModel)
                .carID(UUID.randomUUID())
                .configured(false)
                .config(cfg)
                .build();

        CarEntity carEntity = carMapper.toEntity(car);

        UUID carEntityId = carRepository.save(carEntity).getId();

        // act & assert
        mockMvc.perform(get("/cars/get/" + carEntityId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(carEntityId.toString()));
    }

    @Test
    void shouldReturn404WhenCarNotFound() throws Exception {
        // arrange
        UUID fakeId = UUID.randomUUID();
        UUID anotherFakeId = UUID.randomUUID();


        // act & assert
        mockMvc.perform(get("/cars/get/" + fakeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/cars/get/" + anotherFakeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}