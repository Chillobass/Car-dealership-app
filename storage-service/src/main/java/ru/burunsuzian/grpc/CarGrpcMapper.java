package ru.burunsuzian.grpc;

import org.springframework.stereotype.Component;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.jpa.domainEntities.CarModelEntity;
import ru.burunsuzian.grpc.storage.v1.CarProto;

@Component
public class CarGrpcMapper {
    private boolean isCustomCar(CarEntity entity){
        return (entity.getDecor() != null
                || entity.getSteeringWheel() != null
                || entity.getTransmission() != null
                || entity.getWheels() != null);
    }

    public CarProto toProto(CarEntity entity){
        CarModelEntity carModel = entity.getModel();

        CarProto.Builder builder = CarProto.newBuilder()
                .setCarId(entity.getId().toString())
                .setIsAvailable(entity.isAvailable())
                .setIsCustomCar(isCustomCar(entity));

        if (carModel != null){
            builder.setModelId(carModel.getId().toString())
                    .setBrand(carModel.getBrand())
                    .setColor(carModel.getCarColor())
                    .setBasePrice(carModel.getBasePrice().toString());
        }
        return builder.build();
    }
}
