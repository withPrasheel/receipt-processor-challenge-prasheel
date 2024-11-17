package com.assessment.receiptprocessor.helper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(Map.of("description", "The receipt is invalid"));
    }

    @ExceptionHandler(InvalidReceiptException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReceiptException(InvalidReceiptException ex) {
        return ResponseEntity.badRequest().body(Map.of("description", "The receipt is invalid"));
    }
}

