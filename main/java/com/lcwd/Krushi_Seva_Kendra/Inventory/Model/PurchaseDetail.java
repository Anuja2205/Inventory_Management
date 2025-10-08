package com.lcwd.Krushi_Seva_Kendra.Inventory.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDetail {
    private String itemName;
    private double qty;
    private double rate;
    private double total;
}
