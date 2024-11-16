package com.assessment.receiptprocessor.model;

import lombok.Data;

import java.util.List;

@Data
public class Receipt {
    private String retailer;
    private String purchaseDate; // Format: YYYY-MM-DD
    private String purchaseTime; // Format: HH:mm
    private String total; // Format: x.xx
    private List<Item> items;
}