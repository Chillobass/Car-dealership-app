package org.example.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.application.requests.CarOrderCreateRequest;
import org.example.application.responses.CarOrderResponse;
import org.example.data.persistence.services.CarOrderService;
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
    public ResponseEntity<CarOrderResponse> transition(@PathVariable UUID id, @PathVariable String newState) {
        return new ResponseEntity<>(carOrderService.transitionTo(id, newState), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CarOrderResponse> createCarOrder(CarOrderCreateRequest carCreationRequest){
        return new ResponseEntity<>(carOrderService.createOrder(carCreationRequest.customerId(),
                carCreationRequest.managerId(), carCreationRequest.carId()), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> getCarOrder(@PathVariable("id") UUID carOrderId) {
        String response = carOrderService.getCarOrder(carOrderId).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCarOrder(@PathVariable("id") UUID carId) {
        carOrderService.deleteCarOrder(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}