package com.lcwd.Krushi_Seva_Kendra.Inventory.repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.model.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {

}
