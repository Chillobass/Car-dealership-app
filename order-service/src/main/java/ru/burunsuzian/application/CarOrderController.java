package ru.burunsuzian.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.burunsuzian.application.requests.CarOrderCreateRequest;
import ru.burunsuzian.application.responses.CarOrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.burunsuzian.businessLogic.servicesImpl.UserService;
import ru.burunsuzian.data.persistence.services.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carOrders")
@RequiredArgsConstructor
@Tag(name = "Car Orders")
public class CarOrderController {

    private final CarOrderService carOrderService;

    @PostMapping("/transition/{id}/{newState}")
    @ApiResponse(responseCode = "200", description = "состояние переключено")
    @ApiResponse(responseCode = "404", description = "не удалось создать машину")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'MANAGER')")
    public ResponseEntity<CarOrderResponse> transition(@PathVariable UUID id, @PathVariable String newState) throws JsonProcessingException {
        return new ResponseEntity<>(carOrderService.transitionTo(id, newState), HttpStatus.OK);
    }


    @PostMapping
    @ApiResponse(responseCode = "200", description = "заказ создан")
    @ApiResponse(responseCode = "404", description = "не удалось создать заказ")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'USER', 'MANAGER')")
    public ResponseEntity<CarOrderResponse> createCarOrder(@RequestBody CarOrderCreateRequest carOrderCreationRequest){
        return new ResponseEntity<>(carOrderService.createOrder(UserService.getCurrentUserId(),
                carOrderCreationRequest.managerId(), carOrderCreationRequest.carId()), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or " + "@carOrderService.isOwner(authentication.name, #id)")
    public ResponseEntity<String> getCarOrder(@PathVariable("id") UUID id) {
        String response = carOrderService.getCarOrder(id).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/allOrders")
    @ApiResponse(responseCode = "200", description = "все заказы получены")
    @ApiResponse(responseCode = "403", description = "недостаточно прав для выполнение запроса")
    public ResponseEntity<List<CarOrderResponse>> getAllOrders() {
        return new ResponseEntity<>(carOrderService.getAllCarOrders(), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    public ResponseEntity<Void> deleteCarOrder(@PathVariable("id") UUID carId) {
        carOrderService.deleteCarOrder(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}