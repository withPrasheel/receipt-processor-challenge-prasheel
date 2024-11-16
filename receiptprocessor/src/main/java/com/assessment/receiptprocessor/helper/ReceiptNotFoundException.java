package com.assessment.receiptprocessor.helper;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String id) {
        super("No receipt found for that id: " + id);
    }
}

