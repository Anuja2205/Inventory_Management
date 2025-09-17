package com.lcwd.Krushi_Seva_Kendra.Inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {
    private String itemName;
    private String companyName;
    private double qty;
    private double rate;
    private String batchNo;
    private String packing;
    private String expiry;
    private String billType;

}
