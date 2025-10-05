package com.airBnb.AirBnB.advice;


import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException e) {
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(e.getMessage()).build();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e) {
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(e.getMessage()).build();
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
