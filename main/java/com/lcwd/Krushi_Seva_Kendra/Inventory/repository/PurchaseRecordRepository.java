package com.lcwd.Krushi_Seva_Kendra.Inventory.repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.model.PurchaseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRecordRepository extends MongoRepository<PurchaseRecord, String> {
}
