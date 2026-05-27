package ru.burunsuzian.application;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burunsuzian.grpc.StorageCarsGrpcClient;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "available cars")
public class AvailableCarsController {
    private final StorageCarsGrpcClient storageCarsGrpcClient;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ApiResponse(responseCode = "200", description = "успех. список авто в наличии")
    @ApiResponse(responseCode = "404", description = "storage-service недоступен")
    public ResponseEntity<List<CarGrpcDto>> listAvailableCars(){
        return ResponseEntity.ok(storageCarsGrpcClient.listAvailableCars());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ApiResponse(responseCode = "200", description = "машина найдена")
    @ApiResponse(responseCode = "404", description = "машина не найдена")
    @ApiResponse(responseCode = "412", description = "машина уже не доступна")
    @ApiResponse(responseCode = "503", description = "storage-service недоступен")
    public ResponseEntity<CarGrpcDto> getAvailableCar(@PathVariable("id") UUID id){
        return ResponseEntity.ok(storageCarsGrpcClient.getAvailableCar(id));
    }
}
