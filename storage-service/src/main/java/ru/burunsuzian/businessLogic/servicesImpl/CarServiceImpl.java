package ru.burunsuzian.businessLogic.servicesImpl;

import ru.burunsuzian.application.requests.CarCreationRequest;
import ru.burunsuzian.application.responses.CarResponse;
import ru.burunsuzian.businessLogic.domain.Car;
import ru.burunsuzian.businessLogic.domain.CarModel;
import ru.burunsuzian.businessLogic.mappers.CarMapper;
import ru.burunsuzian.businessLogic.mappers.DetailMapper;
import ru.burunsuzian.businessLogic.mappers.FromStringToReplaceable;
import ru.burunsuzian.businessLogic.mappers.FromToStringIrreplaceable;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.exceptions.IncompatibleComponentException;
import ru.burunsuzian.businessLogic.details.Detail;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.jpa.domainEntities.DetailEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.data.persistence.repositories.DetailRepository;
import ru.burunsuzian.data.persistence.services.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private CarsRepository carsRepository;
    private DetailRepository detailRepository;
    private CarMapper carMapper;
    private DetailMapper detailMapper;

    private void swapDetail(Car currentCar, Detail detail){
        if (detail.isCompatibleWith(currentCar.getModel()) == false){
            throw new IncompatibleComponentException("detail " + detail.getId() + " is not compatible with such car");
        }
        currentCar.getConfig().get(detail.getCategory()).getCategory().setReservedBy(null);
        currentCar.getConfig().remove(detail.getCategory());
        detail.getCategory().setReservedBy(currentCar.getCarId());
        currentCar.getConfig().put(detail.getCategory(), detail);
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCar(UUID carId) {
        CarEntity carEntity = carsRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("no such car in our dealership"));
        return new CarResponse(carEntity.getId());
    }

    @Override
    public List<ReplaceableComponentType> findAvailableDetails(UUID carId, String category) {
        CarEntity carEntity = carsRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("no such car in our dealership"));
        CarModel carModel = carMapper.toDomain(carEntity).getModel();
        return detailRepository
                .findAll().stream()
                .filter(detail -> detail.toDomain().getClass() == (FromStringToReplaceable.convert(category).getClass()))
                .filter(detail -> new Detail(detail.getId(), detail.toDomain(),
                                                        new Money(detail.getPrice()),
                                                        detail.getCompatibleCars()).isCompatibleWith(carModel))
                .collect(Collectors.toList());
    }

    @Override @Transactional
    public CarResponse createConfiguration(UUID carId, UUID decor, UUID steeringWheel, UUID transmission, UUID wheels) {
        CarEntity carEntity = carsRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("no such car in our dealership"));

        Car car = carMapper.toDomain(carEntity);
        Map<Replaceable, Detail> swaps = new HashMap<>();

        addToNewConfig(swaps, decor);
        addToNewConfig(swaps, steeringWheel);
        addToNewConfig(swaps, transmission);
        addToNewConfig(swaps, wheels);

        for (Detail detail : swaps.values()) {
            swapDetail(car, detail);
        }

        car.setConfigured(true);

        carsRepository.save(carMapper.toEntity(car));

        return new CarResponse(carId);
    }

    @Override @Transactional
    public void deleteCar(UUID carId) {
        if (!carsRepository.existsById(carId)) {
            throw new EntityNotFoundException("no such car in our dealership");
        }
        carsRepository.deleteById(carId);
    }

    private void addToNewConfig(Map<Replaceable, Detail> swaps, UUID detailId) {
        if (detailId == null) {
            return;
        }

        var detailEntity = detailRepository.findById(detailId)
                .orElseThrow(() -> new EntityNotFoundException("no such detail"));

        Detail detail = new Detail(
                detailEntity.getId(),
                detailEntity.toDomain(),
                new Money(detailEntity.getPrice()),
                detailEntity.getCompatibleCars()
        );

        swaps.put(detail.getCategory(), detail);
    }

    @Override @Transactional
    public CarResponse createCar(CarCreationRequest request) {
        CarModel model = CarModel.builder()
                .brand(request.brand())
                .modelId(request.modelId())
                .basePrice(new Money(request.basePrice()))
                .carDrive(FromToStringIrreplaceable.convert(request.carDrive()))
                .carColor(request.carColor())
                .carBody(FromToStringIrreplaceable.convert(request.carBody()))
                .fuel(FromToStringIrreplaceable.convert(request.fuel()))
                .enginePower(request.enginePower())
                .engineCapacity(request.engineCapacity())
                .gearBox(FromToStringIrreplaceable.convert(request.gearBox()))
                .build();

        HashMap<Replaceable, Detail> config = new HashMap<>();

        ReplaceableComponentType steeringWheelDetail = detailRepository.findById(request.steeringWheel()).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        ReplaceableComponentType decorDetail = detailRepository.findById(request.decor()).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        ReplaceableComponentType transmissionDetail = detailRepository.findById(request.transmission()).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        ReplaceableComponentType wheelsDetailDetail = detailRepository.findById(request.wheels()).orElseThrow(() -> new EntityNotFoundException("Car not found"));

        UUID carId = UUID.randomUUID();

        steeringWheelDetail.setReservedBy(carId);
        decorDetail.setReservedBy(carId);
        transmissionDetail.setReservedBy(carId);
        wheelsDetailDetail.setReservedBy(carId);

        DetailEntity steeringWheelDetailEntity = DetailEntity.builder()
                        .category(steeringWheelDetail)
                        .price(steeringWheelDetail.getPrice())
                        .compatibleModels(steeringWheelDetail.getCompatibleCars())
                        .id(steeringWheelDetail.getId())
                        .build();

        DetailEntity decorDetailDetailEntity = DetailEntity.builder()
                .category(decorDetail)
                .price(decorDetail.getPrice())
                .compatibleModels(decorDetail.getCompatibleCars())
                .id(decorDetail.getId())
                .build();

        DetailEntity transmissionDetailDetailEntity = DetailEntity.builder()
                .category(transmissionDetail)
                .price(transmissionDetail.getPrice())
                .compatibleModels(transmissionDetail.getCompatibleCars())
                .id(transmissionDetail.getId())
                .build();

        DetailEntity wheelsDetailDetailDetailEntity = DetailEntity.builder()
                .category(wheelsDetailDetail)
                .price(wheelsDetailDetail.getPrice())
                .compatibleModels(wheelsDetailDetail.getCompatibleCars())
                .id(wheelsDetailDetail.getId())
                .build();

        config.put(steeringWheelDetail.toDomain(), detailMapper.toDomain(steeringWheelDetailEntity));
        config.put(decorDetail.toDomain(), detailMapper.toDomain(decorDetailDetailEntity));
        config.put(transmissionDetail.toDomain(), detailMapper.toDomain(transmissionDetailDetailEntity));
        config.put(wheelsDetailDetail.toDomain(), detailMapper.toDomain(wheelsDetailDetailDetailEntity));

        Car car = Car.builder()
                .model(model)
                .configured(false)
                .config(config)
                .build();

        CarEntity carEntity = carMapper.toEntity(car);
        carsRepository.save(carEntity);

        return new CarResponse(carEntity.getId());
    }
}
