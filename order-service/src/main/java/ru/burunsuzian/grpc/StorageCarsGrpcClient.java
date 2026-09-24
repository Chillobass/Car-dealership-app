package ru.burunsuzian.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.burunsuzian.application.CarGrpcDto;
import ru.burunsuzian.businessLogic.exceptions.CarNotAvailableException;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.exceptions.StorageServiceUnavailableException;
import ru.burunsuzian.grpc.storage.v1.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class StorageCarsGrpcClient {
    private static final long DEADLINE_SECONDS = 2;

    @GrpcClient("storage-cars-service")
    private StorageCarsServiceGrpc.StorageCarsServiceBlockingStub stub;

    private CarGrpcDto toDto(CarProto proto){
        return new CarGrpcDto(
                proto.getCarId(),
                proto.getModelId(),
                proto.getBrand(),
                proto.getColor(),
                proto.getBasePrice(),
                proto.getIsCustomCar(),
                proto.getIsAvailable()
        );
    }

    private RuntimeException translate(StatusRuntimeException e, String op){
        Status.Code code = e.getStatus().getCode();
        String desc = e.getStatus().getDescription();
        log.warn("grpc {} failed with code {} , and desc={}", op, code, desc);

        return switch(code){
            case DEADLINE_EXCEEDED -> new StorageServiceUnavailableException(
                    "storage-service didn't respond in time (" + op + ")", e);
            case UNAVAILABLE -> new StorageServiceUnavailableException(
                    "storage-service is not available (" + op + ")", e);
            case NOT_FOUND -> new EntityNotFoundException(desc != null ? desc : "car not found");

            case FAILED_PRECONDITION -> new CarNotAvailableException(desc != null ? desc : "car is not available");

            default -> e;
        };
    }

    public List<CarGrpcDto> listAvailableCars(){
        log.info("grpc storage: listAvailableCars");
        try{
            ListAvailableCarsResponse response = stub.withDeadlineAfter(DEADLINE_SECONDS, TimeUnit.SECONDS)
                    .listAvailableCars(ListAvailableCarsRequest.newBuilder().build());
            log.info("grpc storage: listAvailableCars received {} cars", response.getCarsCount());

            return response.getCarsList().stream().map(this::toDto).toList();
        } catch(StatusRuntimeException e){
            throw translate(e, "listAvailableCars");
        }
    }

    public CarGrpcDto getAvailableCar(UUID carId){
        log.info("grpc storage: getAvailableCar {}", carId);
        try{
            GetAvailableCarResponse response = stub.withDeadlineAfter(DEADLINE_SECONDS, TimeUnit.SECONDS)
                    .getAvailableCar(GetAvailableCarRequest.newBuilder()
                            .setCarId(carId.toString()).build());
            log.info("grpc storage: returned car {}", carId);
            return toDto(response.getCar());
        } catch(StatusRuntimeException e){
            throw translate(e, "getAvailableCar");
        }
    }
}
