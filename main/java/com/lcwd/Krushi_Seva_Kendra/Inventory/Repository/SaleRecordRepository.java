package com.lcwd.Krushi_Seva_Kendra.Inventory.Repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.Model.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {

}
