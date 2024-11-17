package com.assessment.receiptprocessor.helper;

public class InvalidReceiptException extends RuntimeException {
    public InvalidReceiptException(String message) {
        super(message);
    }
}

