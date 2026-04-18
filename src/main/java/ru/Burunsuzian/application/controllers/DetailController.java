package ru.Burunsuzian.application.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import ru.Burunsuzian.application.requests.DetailRequest;
import ru.Burunsuzian.application.responses.DetailResponse;
import ru.Burunsuzian.data.persistence.services.DetailManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/details")
@RequiredArgsConstructor
@Tag(name = "Details", description = "API для управления деталями")
public class DetailController {

    private final DetailManagementService detailManagementService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "создана")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    public ResponseEntity<DetailResponse> addDetail(@RequestBody DetailRequest detailRequest) {
        DetailResponse response = detailManagementService.add(detailRequest.price(), detailRequest.category());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeDetail(@PathVariable("id") UUID detailId) {
        detailManagementService.remove(detailId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    @PutMapping("/{detailId}/compatible-models/{carModelId}")
    public ResponseEntity<UUID> addCompatibleModel(
            @PathVariable("detailId") UUID detailId,
            @PathVariable("carModelId") UUID carModelId
    ) {
        detailManagementService.addCompatibleModel(detailId, carModelId);
        return new ResponseEntity<>(detailId, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    public ResponseEntity<String> getDetail(@PathVariable("id") UUID detailId) {
        String response = detailManagementService.getDetail(detailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}