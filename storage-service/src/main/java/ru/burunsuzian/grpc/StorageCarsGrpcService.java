package ru.burunsuzian.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.data.jpa.domainEntities.CarEntity;
import ru.burunsuzian.data.persistence.repositories.CarsRepository;
import ru.burunsuzian.grpc.storage.v1.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@GrpcService
public class StorageCarsGrpcService extends StorageCarsServiceGrpc.StorageCarsServiceImplBase {
    private final CarsRepository carsRepository;
    private final CarGrpcMapper carGrpcMapper;

    public void listAvailableCars(){}

    @Override
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public void listAvailableCars(ListAvailableCarsRequest request, StreamObserver<ListAvailableCarsResponse> responseObserver){
        log.info("grpc listAvailableCars: incoming request");

        List<CarEntity> available = carsRepository.findAllByIsAvailableTrue();
        List<CarProto> protos = available.stream().map(carGrpcMapper::toProto).toList();

        log.info("grpc listAvailableCars: we have {} cars", protos.size());

        responseObserver.onNext(ListAvailableCarsResponse.newBuilder().addAllCars(protos).build());
        responseObserver.onCompleted();
    }

    public void getAvailableCar(GetAvailableCarRequest request, StreamObserver<GetAvailableCarResponse> responseObserver){
        log.info("grpc GetAvailableCar: car_id={}", request.getCarId());

        UUID carId;
        try{
            carId = UUID.fromString(request.getCarId());
        } catch(IllegalArgumentException e){
            log.warn("grpc GetAvailableCar: invalid UUID");
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("car_id must be a valid UUID").asRuntimeException());
            return;
        }

        Optional<CarEntity> carOrNull = carsRepository.findById(carId);
        if (carOrNull.isEmpty()){
            log.info("grpc GetAvailableCar: car {} not found", carId);
            responseObserver.onError(Status.NOT_FOUND.withDescription("car not found")
                    .asRuntimeException());
            return;
        }

        CarEntity car = carOrNull.get();
        if (car.isAvailable() == false){
            log.info("grpc GetAvailableCar: car {} found but is not available", carId);
            responseObserver.onError(Status.FAILED_PRECONDITION.withDescription("car is not available")
                    .asRuntimeException());
            return;
        }

        responseObserver.onNext(GetAvailableCarResponse.newBuilder()
                .setCar(carGrpcMapper.toProto(car)).build());
        responseObserver.onCompleted();
    }
}
