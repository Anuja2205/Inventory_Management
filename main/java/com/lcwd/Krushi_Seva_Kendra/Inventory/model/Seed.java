package com.lcwd.Krushi_Seva_Kendra.Inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection= "seeds")
public class Seed {
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
