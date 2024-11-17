package com.assessment.receiptprocessor.model;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class Item {
    @NotBlank
    private String shortDescription;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price; // Format: x.xx
}
