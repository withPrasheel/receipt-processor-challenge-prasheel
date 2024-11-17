package com.assessment.receiptprocessor.model;

import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;


@Data
public class Receipt {
    @NotBlank
    private String retailer;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String purchaseDate; // Format: YYYY-MM-DD

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}")
    private String purchaseTime; // Format: HH:mm

    @NotEmpty
    @Valid
    private List<Item> items;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total; // Format: x.xx
}
