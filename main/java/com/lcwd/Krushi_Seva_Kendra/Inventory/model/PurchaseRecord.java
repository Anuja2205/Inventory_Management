package com.lcwd.Krushi_Seva_Kendra.Inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "purchases")
public class PurchaseRecord {
    @Id
    private String id;
    private String seedId;
    private String itemName;
    private String companyName;
    private double purchasedQty;
    private double purchasedRate;
    private String date;
    private String invoiceNumber;
    private String billType;
}
