package ru.Burunsuzian.application.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.Burunsuzian.application.requests.TestDriveRequest;
import ru.Burunsuzian.application.responses.TestDriveResponse;
import ru.Burunsuzian.data.persistence.services.TestDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/testDrive")
@RequiredArgsConstructor
@Tag(name = "Test Drives")
public class TestDriveController {
    private final TestDriveService testDriveService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "создано")
    @ApiResponse(responseCode = "404", description = "не удалось создать")
    public ResponseEntity<TestDriveResponse> createTestDriveRequest(@RequestBody TestDriveRequest testDriveRequest){
        return new ResponseEntity<>(testDriveService.createTestDriveRequest(
                testDriveRequest.customerId(), testDriveRequest.carId(), testDriveRequest.time()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    public ResponseEntity<UUID> markFinished(@PathVariable("id") UUID testDriveId){
        testDriveService.markFinished(testDriveId);
        return new ResponseEntity<>(testDriveId, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    @ApiResponse(responseCode = "404", description = "не удалось выполнить")
    public ResponseEntity<TestDriveResponse> getTestDrive(@PathVariable("id") UUID testDriveId){
        TestDriveResponse response = testDriveService.getTestDrive(testDriveId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "200", description = "выполнено")
    public ResponseEntity<Void> deleteTestDrive(@PathVariable("id") UUID testDriveId){
        testDriveService.deleteTestDrive(testDriveId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
