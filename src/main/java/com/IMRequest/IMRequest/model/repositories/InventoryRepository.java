package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByArticleIdAndWarehouseId(String articleId, String warehouseId); // Find inventory by article and warehouse
    List<Inventory> findByArticleId(String articleId); // Find inventories by article ID
    List<Inventory> findByWarehouseId(String warehouseId); // Find inventories by warehouse ID
}
