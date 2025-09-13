package com.lcwd.Krushi_Seva_Kendra.Inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String customerName;
    private List<String> purchasedSeed; //ek list customer cha purchase history
}
