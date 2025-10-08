package com.lcwd.Krushi_Seva_Kendra.Inventory.Repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.Model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByCustomerName(String customerName);  
}
