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
    private double purchasedQty;
    private String date;
    private String invoiceNumber;
}
