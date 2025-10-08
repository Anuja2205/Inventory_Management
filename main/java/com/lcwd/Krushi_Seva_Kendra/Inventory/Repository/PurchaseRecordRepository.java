package com.lcwd.Krushi_Seva_Kendra.Inventory.Repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.Model.PurchaseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRecordRepository extends MongoRepository<PurchaseRecord, String> {
}
