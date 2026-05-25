package ru.burunsuzian.businessLogic.mappers;

import ru.burunsuzian.businessLogic.domain.CarModel;
import ru.burunsuzian.businessLogic.details.irreplaceable.Irreplaceable;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import ru.burunsuzian.data.jpa.domainEntities.CarModelEntity;
import ru.burunsuzian.data.jpa.irreplaceableEntities.IrreplaceableComponentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CarModelMapper {

    @Mapping(target = "modelId", source = "id")
    @Mapping(target = "basePrice", source = "basePrice", qualifiedByName = "intToMoney")
    @Mapping(target = "carDrive", expression = "java(mapDrive(entity.getCarDrive()))")
    @Mapping(target = "carBody", expression = "java(mapBody(entity.getCarBody()))")
    @Mapping(target = "fuel", expression = "java(mapFuel(entity.getFuel()))")
    @Mapping(target = "gearBox", expression = "java(mapGearBox(entity.getGearBox()))")
    CarModel toDomain(CarModelEntity entity);

    @Mapping(target = "id", source = "modelId")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "basePrice", source = "basePrice", qualifiedByName = "moneyToInt")
    @Mapping(target = "carDrive", expression = "java(domainToEntity(domain.getCarDrive()))")
    @Mapping(target = "carBody", expression = "java(domainToEntity(domain.getCarBody()))")
    @Mapping(target = "fuel", expression = "java(domainToEntity(domain.getFuel()))")
    @Mapping(target = "gearBox", expression = "java(domainToEntity(domain.getGearBox()))")
    @Mapping(target = "enginePower", source = "enginePower")
    @Mapping(target = "engineCapacity", source = "engineCapacity")
    @Mapping(target = "carColor", source = "carColor")
    CarModelEntity toEntity(CarModel domain);

    @Named("intToMoney")
    default Money intToMoney(int price) {
        return new Money(price);
    }

    @Named("moneyToInt")
    default int moneyToInt(Money price) {
        return price.getMoney().intValue();
    }

    @Named("carDriveToEntity")
    default IrreplaceableComponentType domainToEntity(Irreplaceable irreplaceable) {
        return irreplaceable != null ? irreplaceable.toEntity() : null;
    }

    @Named("mapDrive")
    default Irreplaceable mapDrive(IrreplaceableComponentType entity) {
        return entity != null ? entity.toDomain() : null;
    }

    @Named("mapBody")
    default Irreplaceable mapBody(IrreplaceableComponentType entity) {
        return entity != null ? entity.toDomain() : null;
    }

    @Named("mapFuel")
    default Irreplaceable mapFuel(IrreplaceableComponentType entity) {
        return entity != null ? entity.toDomain() : null;
    }

    @Named("mapGearBox")
    default Irreplaceable mapGearBox(IrreplaceableComponentType entity) {
        return entity != null ? entity.toDomain() : null;
    }
}
