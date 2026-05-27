package ru.burunsuzian.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.burunsuzian.businessLogic.exceptions.*;

import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                Instant.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> handleJsonError(JsonProcessingException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncompatibleComponentException.class)
    public ResponseEntity<ErrorResponse> handleIncompatibleComponentException(IncompatibleComponentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                Instant.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarOrderTransitionException.class)
    public ResponseEntity<ErrorResponse> handleCarOrderTransitionException(CarOrderTransitionException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.IM_USED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal server error: " + exception.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StorageServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleStorageUnavailable(StorageServiceUnavailableException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), Instant.now()),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CarNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleCarNotAvailable(CarNotAvailableException e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), Instant.now()),
                HttpStatus.PRECONDITION_FAILED);
    }
}