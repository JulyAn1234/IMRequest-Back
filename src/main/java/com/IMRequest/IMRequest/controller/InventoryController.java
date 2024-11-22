package com.IMRequest.IMRequest.controller;

import com.IMRequest.IMRequest.model.entities.Inventory;
import com.IMRequest.IMRequest.model.responses.AggregatedInventory;
import com.IMRequest.IMRequest.model.responses.EnrichedInventoryWithWarehouse;
import com.IMRequest.IMRequest.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // 1. Get stock of an article in a specific warehouse
    @GetMapping("/getInventory/{articleId}/{warehouseId}")
    public ResponseEntity<?> getInventory(@PathVariable String articleId, @PathVariable String warehouseId) {
        try {
            Optional<Inventory> inventory = inventoryService.getInventory(articleId, warehouseId);
            return inventory.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. Create a new inventory record
    @PostMapping("/createInventory")
    public ResponseEntity<?> createInventory(@RequestBody Inventory inventory) {
        try {
            Inventory createdInventory = inventoryService.createInventory(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Edit an existing inventory (update stock for an article in a warehouse)
    @PostMapping("/editInventory/{articleId}/{warehouseId}")
    public ResponseEntity<?> editInventory(@PathVariable String articleId, @PathVariable String warehouseId, @RequestBody Inventory inventory) {
        try {
            Optional<Inventory> updatedInventory = inventoryService.editInventory(articleId, warehouseId, inventory.getStock());
            return updatedInventory.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4. Get the total stock of an article in all warehouses belonging to a specific Unidad
    @GetMapping("/getTotalStockByArticleInUnidad/{articleId}/{unidadId}")
    public ResponseEntity<?> getTotalStockByArticleInUnidad(@PathVariable String articleId, @PathVariable int unidadId) {
        try {
            int totalStock = inventoryService.getTotalStockByArticleInUnidad(articleId, unidadId);
            return totalStock > 0 ? ResponseEntity.ok(totalStock)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stock found for the article in the given Unidad");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 5. Get aggregated inventory for all articles in a specific Unidad
    @GetMapping("/getAggregatedInventoryByUnidad/{unidadId}")
    public ResponseEntity<?> getAggregatedInventoryByUnidad(@PathVariable int unidadId) {
        try {
            List<AggregatedInventory> aggregatedInventories = inventoryService.getAggregatedInventoryByUnidad(unidadId);
            return aggregatedInventories.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No aggregated inventory found")
                    : ResponseEntity.ok(aggregatedInventories);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 6. Get a list of inventories for a specific article in a specific Unidad
    @GetMapping("/getInventoriesForArticleInUnidad/{articleId}/{unidadId}")
    public ResponseEntity<?> getInventoriesForArticleInUnidad(@PathVariable String articleId, @PathVariable int unidadId) {
        try {
            List<EnrichedInventoryWithWarehouse> inventories = inventoryService.getInventoriesForArticleInUnidad(articleId, unidadId);
            return inventories.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No inventories found")
                    : ResponseEntity.ok(inventories);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 7. Get all aggregated inventories for all articles in a given warehouse
    @GetMapping("/getAggregatedInventoryByWarehouse/{warehouseId}")
    public ResponseEntity<?> getAggregatedInventoryByWarehouse(@PathVariable String warehouseId) {
        try {
            List<AggregatedInventory> aggregatedInventories = inventoryService.getAggregatedInventoryByWarehouse(warehouseId);
            return aggregatedInventories.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No aggregated inventory found")
                    : ResponseEntity.ok(aggregatedInventories);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
