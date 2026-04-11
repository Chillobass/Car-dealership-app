package org.example.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.application.requests.CarOrderCreateRequest;
import org.example.application.responses.CarOrderResponse;
import org.example.data.persistence.services.CarOrderService;
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
        return carOrderService.transitionTo(id, newState);
    }


    @PostMapping
    public ResponseEntity<CarOrderResponse> createCarOrder(CarOrderCreateRequest carCreationRequest){
        return carOrderService.createOrder(carCreationRequest.customerId(), carCreationRequest.managerId(), carCreationRequest.carId());
    }
}