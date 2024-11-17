package com.assessment.receiptprocessor.controller;

import com.assessment.receiptprocessor.helper.InvalidReceiptException;
import com.assessment.receiptprocessor.helper.ReceiptNotFoundException;
import com.assessment.receiptprocessor.model.Receipt;
import com.assessment.receiptprocessor.service.ReceiptService;

import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            throw new ReceiptNotFoundException(id);
        }
        return ResponseEntity.ok(Map.of("points", points));
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReceiptNotFoundException(ReceiptNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("description", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(Map.of("description", "The receipt is invalid"));
    }

    @ExceptionHandler(InvalidReceiptException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReceiptException(InvalidReceiptException ex) {
        return ResponseEntity.badRequest().body(Map.of("description", "The receipt is invalid"));
    }
}
