package com.ecommerceAuth.ecommerceAuth.model.repositories;

import com.ecommerceAuth.ecommerceAuth.model.entities.Article;
import com.ecommerceAuth.ecommerceAuth.model.entities.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String>{
}
