package org.example.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.application.requests.DetailRequest;
import org.example.application.responses.DetailResponse;
import org.example.data.persistence.services.DetailManagementService;
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
    public ResponseEntity<DetailResponse> addDetail(@RequestBody DetailRequest detailRequest) {
        DetailResponse response = detailManagementService.add(detailRequest.price(), detailRequest.category());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeDetail(@PathVariable("id") UUID detailId) {
        detailManagementService.remove(detailId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{detailId}/compatible-models/{carModelId}")
    public ResponseEntity<UUID> addCompatibleModel(
            @PathVariable("detailId") UUID detailId,
            @PathVariable("carModelId") UUID carModelId
    ) {
        detailManagementService.addCompatibleModel(detailId, carModelId);
        return new ResponseEntity<>(detailId, HttpStatus.OK);
    }
}