package org.example.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.application.requests.CarCreationRequest;
import org.example.application.responses.CarResponse;
import org.example.data.jpa.domainEntities.DetailEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;
import org.example.data.persistence.services.CarService;
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
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") UUID carId) {
        CarResponse response = carService.getCar(carId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{carId}/available-details/{category}")
    public ResponseEntity<List<ReplaceableComponentType>> findAvailableDetails(
            @PathVariable("carId") UUID carId,
            @PathVariable("category") String category
    ) {
        List<ReplaceableComponentType> response = carService.findAvailableDetails(carId, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{carId}/configure")
    public ResponseEntity<CarResponse> createConfiguration(
            @PathVariable("carId") UUID carId,
            @RequestParam(required = false) UUID decor,
            @RequestParam(required = false) UUID steeringWheel,
            @RequestParam(required = false) UUID transmission,
            @RequestParam(required = false) UUID wheels
    ) {
        CarResponse response = carService.createConfiguration(carId, decor, steeringWheel, transmission, wheels);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(CarCreationRequest carCreationRequest){
        return new ResponseEntity<>(carService.createCar(carCreationRequest), HttpStatus.CREATED);
    }
}