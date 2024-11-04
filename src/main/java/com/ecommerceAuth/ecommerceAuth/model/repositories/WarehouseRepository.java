package com.ecommerceAuth.ecommerceAuth.model.repositories;

import com.ecommerceAuth.ecommerceAuth.model.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
