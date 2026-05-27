package ru.burunsuzian.application;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.burunsuzian.application.requests.CarCreationRequest;
import ru.burunsuzian.application.requests.ConfigRequest;
import ru.burunsuzian.application.responses.CarResponse;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import ru.burunsuzian.data.persistence.services.CarService;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'USER', 'MANAGER')")
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") UUID carId) {
        CarResponse response = carService.getCar(carId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{carId}/available-details/{category}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "не нашел доступных деталей")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'USER', 'MANAGER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'USER', 'MANAGER')")
    public ResponseEntity<CarResponse> createConfiguration(ConfigRequest configRequest) {
        CarResponse response = carService.createConfiguration(configRequest.carId(), configRequest.decor(),
                configRequest.steeringWheel(), configRequest.transmission(), configRequest.wheels());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "машина создана")
    @ApiResponse(responseCode = "404", description = "не удалось создать машину")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<CarResponse> createCar(CarCreationRequest carCreationRequest){
        return new ResponseEntity<>(carService.createCar(carCreationRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/availability")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "не удалось найти такую машину")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<Void> setAvailability( @PathVariable("id") UUID carId, @RequestParam("available") boolean available){
        carService.setAvailability(carId, available);
        return ResponseEntity.ok().build();
    }
}