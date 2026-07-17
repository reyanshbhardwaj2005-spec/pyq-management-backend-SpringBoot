package com.iiitsonepat.questionbank.exception;

import com.iiitsonepat.questionbank.dto.resopnse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(DuplicatePaperException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicatePaper(DuplicatePaperException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Object>> handleFileStorage(FileStorageException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .success(false)
                        .message(message)
                        .data(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {

        ex.printStackTrace();   // Print full stack trace in Railway logs

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getClass().getSimpleName() + ": " + ex.getMessage())
                        .data(null)
                        .build());
    }

}