package com.lcwd.Krushi_Seva_Kendra.Inventory.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellRequest {
    private String itemName;
    private String companyName;
    private double qty;
    private double rate;
    private String invoiceNo;
    private String customerName;
    private String billType;
}
