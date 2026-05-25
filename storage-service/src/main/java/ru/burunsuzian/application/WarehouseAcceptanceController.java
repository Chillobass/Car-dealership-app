package ru.burunsuzian.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.burunsuzian.application.responses.WarehouseAcceptanceResponse;
import ru.burunsuzian.data.persistence.services.WarehouseAcceptanceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/warehouse_acceptance")
@RequiredArgsConstructor
@Tag(name = "Warehouse Acceptance", description = "API для проверки возможности собрать заказ")
public class WarehouseAcceptanceController {
    private final WarehouseAcceptanceService warehouseAcceptanceService;

    @GetMapping("/get/{carOrderId}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @ApiResponse(responseCode = "404", description = "сборка не найдена")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN', 'MANAGER')")
    public ResponseEntity<List<WarehouseAcceptanceResponse>> getWarehouseAcceptance(@PathVariable("carOrderId") UUID carOrderId) {
        List<WarehouseAcceptanceResponse> responses = warehouseAcceptanceService.getWarehouseAcceptance(carOrderId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<Void> deleteWarehouseAcceptance(@PathVariable("id") UUID WarehouseAcceptanceId) {
        warehouseAcceptanceService.deleteWarehouseAcceptance(WarehouseAcceptanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create/{carOrderId}")
    @ApiResponse(responseCode = "201", description = "Запрос на склад создан")
    @ApiResponse(responseCode = "404", description = "Не удалось создать запрос на склад")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<UUID> createWarehouseAcceptance(@PathVariable("carOrderId") UUID carOrderId, @RequestParam(defaultValue = "true") boolean isCustomCar){
        return new ResponseEntity<>(warehouseAcceptanceService.createWarehouseAcceptance(carOrderId, isCustomCar), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @ApiResponse(responseCode = "200", description = "запрос выполнился успешно")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    public ResponseEntity<String> updateWarehouseAcceptance(@PathVariable("id") UUID WarehouseAcceptanceId) throws JsonProcessingException {
        String state = warehouseAcceptanceService.updateWarehouseAcceptance(WarehouseAcceptanceId);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
