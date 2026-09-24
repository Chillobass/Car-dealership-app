package ru.burunsuzian.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {
    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck(){
        boolean dbUp = isDatabaseUp();

        Map<String, Object> response = Map.of(
                "service", "storage-service",
                "status", dbUp ? "UP" : "DOWN",
                "database", dbUp ? "UP" : "DOWN"
        );
        return dbUp ? ResponseEntity.ok(response) : ResponseEntity.status(503).body(response);
    }

    private boolean isDatabaseUp(){
        try {
            jdbcTemplate.execute("SELECT 1");
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
