package ru.Burunsuzian.application.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import ru.Burunsuzian.application.requests.CarCreationRequest;
import ru.Burunsuzian.application.requests.ConfigRequest;
import ru.Burunsuzian.application.responses.CarResponse;
import ru.Burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.Burunsuzian.data.persistence.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Cars")
public class CarController {

    private final CarService carService;

    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "машина не найдена")
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") UUID carId) {
        CarResponse response = carService.getCar(carId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{carId}/available-details/{category}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "не нашел доступных деталей")
    public ResponseEntity<List<ReplaceableComponentType>> findAvailableDetails(
            @PathVariable("carId") UUID carId,
            @PathVariable("category") String category
    ) {
        List<ReplaceableComponentType> response = carService.findAvailableDetails(carId, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{carId}/configure")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "не удалось создать конфигурацию")
    public ResponseEntity<CarResponse> createConfiguration(ConfigRequest configRequest) {
        CarResponse response = carService.createConfiguration(configRequest.carId(), configRequest.decor(),
                configRequest.steeringWheel(), configRequest.transmission(), configRequest.wheels());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "машина создана")
    @ApiResponse(responseCode = "404", description = "не удалось создать машину")
    public ResponseEntity<CarResponse> createCar(CarCreationRequest carCreationRequest){
        return new ResponseEntity<>(carService.createCar(carCreationRequest), HttpStatus.CREATED);
    }
}