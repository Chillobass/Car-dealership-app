package org.example.application.controllers;

import org.example.application.requests.TestDriveRequest;
import org.example.application.responses.TestDriveResponse;
import org.example.data.persistence.services.TestDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/testDrive")
@RequiredArgsConstructor
@Tag(name = "Test Drives") //swagger swags
public class TestDriveController {
    private final TestDriveService testDriveService;

    @PostMapping
    public ResponseEntity<TestDriveResponse> createTestDriveRequest(@RequestBody TestDriveRequest testDriveRequest){
        return new ResponseEntity<>(testDriveService.createTestDriveRequest(
                testDriveRequest.customerId(), testDriveRequest.carId(), testDriveRequest.time()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UUID> markFinished(@PathVariable("id") UUID testDriveId){
        testDriveService.markFinished(testDriveId);
        return new ResponseEntity<>(testDriveId, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestDriveResponse> getTestDrive(@PathVariable("id") UUID testDriveId){
        TestDriveResponse response = testDriveService.getTestDrive(testDriveId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTestDrive(@PathVariable("id") UUID testDriveId){
        testDriveService.deleteTestDrive(testDriveId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
