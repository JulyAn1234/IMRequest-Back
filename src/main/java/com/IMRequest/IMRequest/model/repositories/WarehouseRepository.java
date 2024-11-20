package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    List<Warehouse> findByUnidad(int unidad); // Get warehouses by Unidad number
}

