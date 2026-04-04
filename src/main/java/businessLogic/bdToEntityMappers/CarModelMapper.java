package businessLogic.bdToEntityMappers;

import businessLogic.CarModel;
import businessLogic.details.irreplaceable.bodyType.BodyType;
import businessLogic.details.irreplaceable.driveType.DriveType;
import businessLogic.details.irreplaceable.fuelType.FuelType;
import businessLogic.details.irreplaceable.gearBoxType.GearBoxType;
import businessLogic.domain.valueObjects.Money;
import data.jpa.domainEntities.CarModelEntity;
import data.jpa.irreplaceableEntities.IrreplaceableComponentType;
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
    @Mapping(target = "carDrive", expression = "java(carDriveToEntity(domain.getCarDrive()))")
    @Mapping(target = "carBody", expression = "java(carBodyToEntity(domain.getCarBody()))")
    @Mapping(target = "fuel", expression = "java(fuelToEntity(domain.getFuel()))")
    @Mapping(target = "gearBox", expression = "java(gearBoxToEntity(domain.getGearBox()))")
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
    default IrreplaceableComponentType carDriveToEntity(DriveType driveType) {
        return driveType != null ? driveType.toEntity() : null;
    }

    @Named("carBodyToEntity")
    default IrreplaceableComponentType carBodyToEntity(BodyType bodyType) {
        return bodyType != null ? bodyType.toEntity() : null;
    }

    @Named("fuelToEntity")
    default IrreplaceableComponentType fuelToEntity(FuelType fuelType) {
        return fuelType != null ? fuelType.toEntity() : null;
    }

    @Named("gearBoxToEntity")
    default IrreplaceableComponentType gearBoxToEntity(GearBoxType gearBoxType) {
        return gearBoxType != null ? gearBoxType.toEntity() : null;
    }

    @Named("mapDrive")
    default DriveType mapDrive(IrreplaceableComponentType entity) {
        return entity != null ? (DriveType) entity.toDomain() : null;
    }

    @Named("mapBody")
    default BodyType mapBody(IrreplaceableComponentType entity) {
        return entity != null ? (BodyType) entity.toDomain() : null;
    }

    @Named("mapFuel")
    default FuelType mapFuel(IrreplaceableComponentType entity) {
        return entity != null ? (FuelType) entity.toDomain() : null;
    }

    @Named("mapGearBox")
    default GearBoxType mapGearBox(IrreplaceableComponentType entity) {
        return entity != null ? (GearBoxType) entity.toDomain() : null;
    }
}
