package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
