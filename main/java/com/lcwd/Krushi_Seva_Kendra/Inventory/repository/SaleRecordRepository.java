package com.lcwd.Krushi_Seva_Kendra.Inventory.repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.model.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
    Optional<SaleRecord> findByInvoiceNumber(String invoiceNumber);
}
