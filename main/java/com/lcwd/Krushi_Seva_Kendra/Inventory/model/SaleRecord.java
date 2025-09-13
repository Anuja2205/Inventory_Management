package com.lcwd.Krushi_Seva_Kendra.Inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sales")
public class SaleRecord {
    @Id
    private String id;
    private String seedId;
    private double soldQty;
    private String date;
    private String invoiceNumber;
}
