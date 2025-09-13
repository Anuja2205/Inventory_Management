package com.lcwd.Krushi_Seva_Kendra.Inventory.repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByCustomerName(String customerName);
}
