package ru.Burunsuzian.application.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.Burunsuzian.application.requests.CarOrderCreateRequest;
import ru.Burunsuzian.application.responses.CarOrderResponse;
import ru.Burunsuzian.data.persistence.services.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carOrders")
@RequiredArgsConstructor
@Tag(name = "Car Orders")
public class CarOrderController {

    private final CarOrderService carOrderService;

    @PostMapping("/transition/{id}/{newState}")
    @ApiResponse(responseCode = "201", description = "состояние переключено")
    @ApiResponse(responseCode = "404", description = "не удалось создать машину")
    public ResponseEntity<CarOrderResponse> transition(@PathVariable UUID id, @PathVariable String newState) {
        return new ResponseEntity<>(carOrderService.transitionTo(id, newState), HttpStatus.OK);
    }


    @PostMapping
    @ApiResponse(responseCode = "200", description = "заказ создан")
    @ApiResponse(responseCode = "404", description = "не удалось создать заказ")
    public ResponseEntity<CarOrderResponse> createCarOrder(CarOrderCreateRequest carCreationRequest){
        return new ResponseEntity<>(carOrderService.createOrder(carCreationRequest.customerId(),
                carCreationRequest.managerId(), carCreationRequest.carId()), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    public ResponseEntity<String> getCarOrder(@PathVariable("id") UUID carOrderId) {
        String response = carOrderService.getCarOrder(carOrderId).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    public ResponseEntity<Void> deleteCarOrder(@PathVariable("id") UUID carId) {
        carOrderService.deleteCarOrder(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}