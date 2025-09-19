package com.lcwd.Krushi_Seva_Kendra.Inventory.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeedResponse {
    @Id
    private String billNo;
    private String itemName;
    private String companyName;
    private String batchNo;
    private String packing;
    private String expiry;
    private double quantity;
    private double rate;
    private double total;
    private String billType;
    private String invoiceNumber;
}
