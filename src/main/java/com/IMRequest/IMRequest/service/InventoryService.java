package com.IMRequest.IMRequest.service;
import com.IMRequest.IMRequest.Exceptions.EntityNotFoundException;
import com.IMRequest.IMRequest.model.entities.Article;
import com.IMRequest.IMRequest.model.entities.Inventory;
import com.IMRequest.IMRequest.model.entities.Warehouse;
import com.IMRequest.IMRequest.model.repositories.ArticleRepository;
import com.IMRequest.IMRequest.model.repositories.InventoryRepository;
import com.IMRequest.IMRequest.model.repositories.WarehouseRepository;
import com.IMRequest.IMRequest.model.responses.AggregatedInventory;
import com.IMRequest.IMRequest.model.responses.EnrichedInventoryWithWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ArticleRepository articleRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ArticleRepository articleRepository, WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.articleRepository = articleRepository;
        this.warehouseRepository = warehouseRepository;
    }

    // 1. Get stock of an article in a specific warehouse
    public Optional<Inventory> getInventory(String articleId, String warehouseId) {
        return inventoryRepository.findByArticleIdAndWarehouseId(articleId, warehouseId);
    }

    // 2. Create a new inventory record
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // 3. Edit an existing inventory (update stock for an article in a warehouse)
    public Optional<Inventory> editInventory(String articleId, String warehouseId, int newStock) {
        Optional<Inventory> existingInventory = inventoryRepository.findByArticleIdAndWarehouseId(articleId, warehouseId);
        return existingInventory.map(inventory -> {
            inventory.setStock(newStock);
            return inventoryRepository.save(inventory);
        });
    }

    // 4. Get the total stock of an article in all warehouses belonging to a specific Unidad
    public int getTotalStockByArticleInUnidad(String articleId, int unidadId) {
        // Find all warehouses belonging to the given Unidad
        List<Warehouse> warehousesInUnidad = warehouseRepository.findByUnidad(unidadId);

        // Filter inventories for the given article and sum stock from warehouses in the given Unidad
        List<Inventory> inventoriesForArticle = inventoryRepository.findByArticleId(articleId);
        return inventoriesForArticle.stream()
                .filter(inventory -> warehousesInUnidad.stream()
                        .anyMatch(warehouse -> warehouse.getId().equals(inventory.getWarehouseId())))
                .mapToInt(Inventory::getStock)
                .sum();
    }

    // 5. Get aggregated inventory for all active articles in a specific Unidad
    public List<AggregatedInventory> getAggregatedInventoryByUnidad(int unidadId) {
        // Get all articles that are active
        List<Article> activeArticles = articleRepository.findAll().stream()
                .filter(Article::isActive)
                .collect(Collectors.toList());

        // Aggregate total stock for each active article across all warehouses in the specified Unidad
        List<AggregatedInventory> aggregatedInventories = new ArrayList<>();
        for (Article article : activeArticles) {
            int totalStock = getTotalStockByArticleInUnidad(article.getId(), unidadId);
                AggregatedInventory aggregatedInventory = AggregatedInventory.builder()
                        .article(article)
                        .totalStock(totalStock)
                        .build();
                aggregatedInventories.add(aggregatedInventory);
        }

        // Return the aggregated inventories for all active articles in the given Unidad
        return aggregatedInventories;
    }

    // 6. Get aggregated inventory for a specific article in a specific Unidad
    public AggregatedInventory getAggregatedInventoryForArticleInUnidad(String articleId, int unidadId) {
        // Check if the article is active
        Optional<Article> optionalArticle = articleRepository.findByIdAndIsActiveTrue(articleId);
        if (!optionalArticle.isPresent()) {
            throw new EntityNotFoundException("Article not found or inactive");
        }

        // Get the total stock for the given article in all warehouses of the specified Unidad
        int totalStock = getTotalStockByArticleInUnidad(articleId, unidadId);

        // If there's any stock, return the aggregated inventory for that article
        if (totalStock > 0) {
            return AggregatedInventory.builder()
                    .article(optionalArticle.get())
                    .totalStock(totalStock)
                    .build();
        } else {
            throw new EntityNotFoundException("No stock found for article in specified Unidad");
        }
    }

    // 7. Get a list of inventories for a specific article in a specific Unidad
    public List<EnrichedInventoryWithWarehouse> getInventoriesForArticleInUnidad(String articleId, int unidadId) {
        // Get all warehouses that belong to the specified Unidad
        List<Warehouse> warehousesInUnidad = warehouseRepository.findByUnidad(unidadId);

        // Get all inventories for the given article
        List<Inventory> inventoriesForArticle = inventoryRepository.findByArticleId(articleId);

        // Filter inventories based on whether they are in a warehouse that belongs to the specified Unidad
        List<EnrichedInventoryWithWarehouse> enrichedInventories = new ArrayList<>();
        for (Inventory inventory : inventoriesForArticle) {
            for (Warehouse warehouse : warehousesInUnidad) {
                if (warehouse.getId().equals(inventory.getWarehouseId())) {
                    EnrichedInventoryWithWarehouse enrichedInventory = EnrichedInventoryWithWarehouse.builder()
                            .warehouse(warehouse)
                            .stock(inventory.getStock())
                            .build();
                    enrichedInventories.add(enrichedInventory);
                }
            }
        }

        return enrichedInventories;
    }

    // 8. Get all inventories for a given warehouse, return aggregated stock for each article
    public List<AggregatedInventory> getAggregatedInventoryByWarehouse(String warehouseId) {
        // Get all articles that are active
        List<Article> activeArticles = articleRepository.findAll().stream()
                .filter(Article::isActive)
                .collect(Collectors.toList());

        // Aggregate total stock for each active article in the specified warehouse
        List<AggregatedInventory> aggregatedInventories = new ArrayList<>();
        for (Article article : activeArticles) {
            Optional<Inventory> inventory = inventoryRepository.findByArticleIdAndWarehouseId(article.getId(), warehouseId);
            inventory.ifPresent(value -> {
                AggregatedInventory aggregatedInventory = AggregatedInventory.builder()
                        .article(article)
                        .totalStock(value.getStock())
                        .Quantity(0)
                        .build();
                aggregatedInventories.add(aggregatedInventory);
            });
        }

        // Return the aggregated inventories for all active articles in the given warehouse
        return aggregatedInventories;
    }
}
