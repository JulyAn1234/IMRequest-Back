package com.IMRequest.IMRequest.service;

import com.IMRequest.IMRequest.model.entities.Article;
import com.IMRequest.IMRequest.model.entities.Inventory;
import com.IMRequest.IMRequest.model.entities.Warehouse;
import com.IMRequest.IMRequest.model.repositories.ArticleRepository;
import com.IMRequest.IMRequest.model.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ArticleRepository articleRepository;  // Inject ArticleRepository to get all articles
    private final InventoryService inventoryService;  // Inject InventoryService to create inventory records

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, ArticleRepository articleRepository, InventoryService inventoryService) {
        this.warehouseRepository = warehouseRepository;
        this.articleRepository = articleRepository;
        this.inventoryService = inventoryService;
    }

    // 1. Create warehouse and initialize inventory for each article in the new warehouse
    public Warehouse createWarehouse(Warehouse warehouse) {
        // Save the warehouse first
        Warehouse createdWarehouse = warehouseRepository.save(warehouse);

        // Get all articles from the database
        List<Article> articles = articleRepository.findAll();

        // Create inventory records for each article with initial stock of 0
        for (Article article : articles) {
            Inventory inventory = new Inventory();
            inventory.setArticleId(article.getId());
            inventory.setWarehouseId(createdWarehouse.getId());
            inventory.setStock(0);  // Initialize with stock 0
            inventoryService.createInventory(inventory);  // Create inventory for the article in the new warehouse
        }

        // Return the created warehouse
        return createdWarehouse;
    }

    // 2. Get all warehouses
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // 3. Get warehouse by ID
    public Optional<Warehouse> getWarehouseById(String id) {
        return warehouseRepository.findById(id);
    }

    // 4. Edit warehouse details
    public Optional<Warehouse> editWarehouse(String id, Warehouse updatedWarehouse) {
        return warehouseRepository.findById(id).map(warehouse -> {
            warehouse.setActive(updatedWarehouse.isActive());
            warehouse.setName(updatedWarehouse.getName());
            warehouse.setUnidad(updatedWarehouse.getUnidad());
            return warehouseRepository.save(warehouse);
        });
    }
}