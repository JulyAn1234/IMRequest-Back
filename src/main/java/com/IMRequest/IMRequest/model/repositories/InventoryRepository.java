package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String>{
}
