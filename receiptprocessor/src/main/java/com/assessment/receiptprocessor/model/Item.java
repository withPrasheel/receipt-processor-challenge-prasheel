package com.assessment.receiptprocessor.model;

import lombok.Data;

@Data
public class Item {
    private String shortDescription;
    private String price; // Format: x.xx
}
