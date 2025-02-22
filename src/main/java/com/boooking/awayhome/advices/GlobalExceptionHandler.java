package com.boooking.awayhome.advices;

import com.boooking.awayhome.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<ApiResponse<?>> handleResouceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=ApiError.builder()
                ._status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return builderErrorResponsibleEntity(apiError);



    }

    private ResponseEntity<ApiResponse<?>> builderErrorResponsibleEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.get_status());

    }

}
