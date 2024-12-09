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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final WarehouseRepository warehouseRepository;  // Inject warehouse repository
    private final InventoryService inventoryService;  // Inject inventory service

    @Autowired
    public ArticleService(ArticleRepository articleRepository, WarehouseRepository warehouseRepository, InventoryService inventoryService) {
        this.articleRepository = articleRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryService = inventoryService;
    }

    // 1. Create article and initialize inventory for all warehouses
    public Article createArticle(Article article) {
        // Save the article first
        Article createdArticle = articleRepository.save(article);

        // Get all warehouses
        List<Warehouse> warehouses = warehouseRepository.findAll();

        // Create inventory records for each warehouse with initial stock of 0
        for (Warehouse warehouse : warehouses) {
            Inventory inventory = new Inventory();
            inventory.setArticleId(createdArticle.getId());
            inventory.setWarehouseId(warehouse.getId());
            inventory.setStock(0);  // Initialize with stock 0
            inventoryService.createInventory(inventory);  // Create inventory for the article in each warehouse
        }

        // Return the created article
        return createdArticle;
    }

    // 2. Get article by ID
    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    // 3. Get all articles
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // 4. Edit an article's details
    public Optional<Article> editArticle(String id, Article updatedArticle) {
        return articleRepository.findById(id).map(article -> {
            article.setActive(updatedArticle.isActive());
            article.setPartida(updatedArticle.getPartida());
            article.setMeasure(updatedArticle.getMeasure());
            article.setDescription(updatedArticle.getDescription());
            article.setName(updatedArticle.getName());
            article.setTool(updatedArticle.isTool());

            return articleRepository.save(article);
        });
    }
}