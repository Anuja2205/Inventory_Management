package com.lcwd.Krushi_Seva_Kendra.Inventory.Repository;

import com.lcwd.Krushi_Seva_Kendra.Inventory.Model.Seed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SeedRepository extends MongoRepository<Seed, String>{
    Optional<Seed> findByItemNameAndCompanyName(String itemName, String companyName);
}