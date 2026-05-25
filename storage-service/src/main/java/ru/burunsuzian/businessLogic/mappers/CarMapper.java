package ru.burunsuzian.businessLogic.mappers;

import ru.burunsuzian.businessLogic.domain.Car;
import ru.burunsuzian.businessLogic.details.Detail;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;

import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashMap;

@Mapper(componentModel = "spring", uses = {CarModelMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CarMapper {

    @Mapping(target = "carId", source = "id")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "config", expression = "java(buildConfig(entity))")
    Car toDomain(CarEntity entity);

    @Mapping(target = "id", source = "carId")
    @Mapping(target = "steeringWheel", expression = "java(extractDetail(domain, ru.burunsuzian.businessLogic.details.replaceable.SteeringWheel.class))")
    @Mapping(target = "decor", expression = "java(extractDetail(domain, ru.burunsuzian.businessLogic.details.replaceable.Decor.class))")
    @Mapping(target = "transmission", expression = "java(extractDetail(domain, ru.burunsuzian.businessLogic.details.replaceable.Transmission.class))")
    @Mapping(target = "wheels", expression = "java(extractDetail(domain, ru.burunsuzian.businessLogic.details.replaceable.Wheels.class))")
    CarEntity toEntity(Car domain);

    default HashMap<Replaceable, Detail> buildConfig(CarEntity entity) {
        HashMap<Replaceable, Detail> config = new HashMap<>();

        config.put(entity.getSteeringWheel().toDomain(), new Detail(
                entity.getSteeringWheel().getId(),
                entity.getSteeringWheel().toDomain(),
                new Money(entity.getSteeringWheel().getPrice()),
                entity.getSteeringWheel().getCompatibleCars()));

        config.put(entity.getDecor().toDomain(), new Detail(
                entity.getDecor().getId(),
                entity.getDecor().toDomain(),
                new Money(entity.getDecor().getPrice()),
                entity.getDecor().getCompatibleCars()));

        config.put(entity.getTransmission().toDomain(), new Detail(
                entity.getTransmission().getId(),
                entity.getTransmission().toDomain(),
                new Money(entity.getTransmission().getPrice()),
                entity.getTransmission().getCompatibleCars()));

        config.put(entity.getWheels().toDomain(), new Detail(
                entity.getWheels().getId(),
                entity.getWheels().toDomain(),
                new Money(entity.getWheels().getPrice()),
                entity.getWheels().getCompatibleCars()));

        return config;
    }

    default ReplaceableComponentType extractDetail(Car domainCar, Class<?> categoryClass) {
        if (domainCar.getConfig() == null) return null;

        return domainCar.getConfig().entrySet().stream()
                .filter(entry -> categoryClass.isInstance(entry.getKey()))
                .map(entry -> entry.getKey().toEntity(
                        entry.getValue().getPrice(),
                        entry.getValue().getCompatibleModels())
                )
                .findFirst()
                .orElse(null);
    }
}
