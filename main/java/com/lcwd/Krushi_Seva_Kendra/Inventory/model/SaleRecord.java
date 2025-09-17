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
    private double soldQty;  //quantity sold
    private double rate;    // market rate at the time of sale
    private double total;  // qty * rate
    private String date;
    private String invoiceNumber;
    private String billType;
}
