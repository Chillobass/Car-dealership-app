package ru.burunsuzian.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burunsuzian.businessLogic.exceptions.StorageServiceUnavailableException;
import ru.burunsuzian.grpc.StorageCarsGrpcClient;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/health")
public class HealthController {
    private final JdbcTemplate jdbcTemplate;
    private final StorageCarsGrpcClient storageCarsGrpcClient;

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck(){
        boolean dbUp = isDatabaseUp();
        boolean grpcUp = isGrpcUp();

        boolean isAllUp = dbUp && grpcUp;

        Map<String, Object> response = Map.of(
                "service", "order-service",
                "status", isAllUp ? "UP" : "DOWN",
                "grpc_storage_service", grpcUp ? "UP" : "DOWN"
        );
        return isAllUp ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);
    }

    private boolean isDatabaseUp(){
        try {
            jdbcTemplate.execute("SELECT 1");
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private boolean isGrpcUp(){
        try {
            storageCarsGrpcClient.listAvailableCars();
            return true;
        } catch (StorageServiceUnavailableException e){
            return false;
        } catch(Exception e){
            return true;
        }
    }
}
