package com.IMRequest.IMRequest.service;

import com.IMRequest.IMRequest.Exceptions.EntityNotFoundException;
import com.IMRequest.IMRequest.Exceptions.InsufficientStockException;
import com.IMRequest.IMRequest.model.entities.*;
import com.IMRequest.IMRequest.model.repositories.ArticleRepository;
import com.IMRequest.IMRequest.model.repositories.LoanRepository;
import com.IMRequest.IMRequest.model.repositories.UserRepository;
import com.IMRequest.IMRequest.model.repositories.WarehouseRepository;
import com.IMRequest.IMRequest.model.responses.EnrichedLoan;
import com.IMRequest.IMRequest.model.responses.EnrichedLoanItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final InventoryService inventoryService;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       InventoryService inventoryService,
                       UserRepository userRepository,
                       WarehouseRepository warehouseRepository,
                       ArticleRepository articleRepository) {
        this.loanRepository = loanRepository;
        this.inventoryService = inventoryService;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
        this.articleRepository = articleRepository;
    }

    // 1. Create new Loan
    public Loan createLoan(Loan loan) {
        // Save the loan
        Loan savedLoan = loanRepository.save(loan);
        // Update inventory for each loan item
        for (LoanItem item : loan.getItems()) {
            // Subtract quantities from the inventory
            updateInventoryForLoanItem(item, loan.getWarehouseId(), -item.getQuantity());
        }
        return savedLoan;
    }

    // 2. Edit the status of one or several items of a given loan to completed (isActive = false)
    public Loan updateLoanItemsToCompleted(String loanId, List<String> articleIds) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        // Update items in the loan
        for (LoanItem item : loan.getItems()) {
            if (articleIds.contains(item.getArticleId()) && item.isActive()) {
                item.setActive(false);
                // Update inventory (sum quantities again)
                updateInventoryForLoanItem(item, loan.getWarehouseId(), item.getQuantity());
            }
        }
        // Save the loan with updated items
        loanRepository.save(loan);
        return loan;
    }

    // 3. Complete all items in the loan and deactivate the loan
    public Loan completeAllLoanItems(String loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        // Update all items to inactive and update inventory
        for (LoanItem item : loan.getItems()) {
            if (item.isActive()) {
                item.setActive(false);
                updateInventoryForLoanItem(item, loan.getWarehouseId(), -item.getQuantity());
            }
        }
        // Set the loan as inactive
        loan.setActive(false);
        // Save the loan with updated status
        loanRepository.save(loan);
        return loan;
    }

    // 4. Get a loan by ID (enriched)
    public EnrichedLoan getEnrichedLoanById(String loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        // Enrich the loan with the corresponding user, warehouse, and items
        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Warehouse warehouse = warehouseRepository.findById(loan.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        List<EnrichedLoanItem> enrichedItems = new ArrayList<>();
        for (LoanItem loanItem : loan.getItems()) {
            Article article = articleRepository.findById(loanItem.getArticleId())
                    .orElseThrow(() -> new EntityNotFoundException("Article not found"));

            EnrichedLoanItem enrichedItem = EnrichedLoanItem.builder()
                    .article(article)
                    .quantity(loanItem.getQuantity())
                    .isActive(loanItem.isActive())
                    .build();
            enrichedItems.add(enrichedItem);
        }

        return EnrichedLoan.builder()
                .id(loan.getId())
                .user(user)
                .warehouse(warehouse)
                .date(loan.getDate())
                .comments(loan.getComments())
                .items(enrichedItems)
                .isActive(loan.isActive())
                .build();
    }

    // 5. Get all loans (enriched)
    public List<EnrichedLoan> getAllEnrichedLoans() {
        List<Loan> loans = loanRepository.findAll();

        List<EnrichedLoan> enrichedLoans = new ArrayList<>();
        for (Loan loan : loans) {
            enrichedLoans.add(getEnrichedLoanById(loan.getId()));
        }
        return enrichedLoans;
    }

    // Helper method to update inventory for a loan item
    private void updateInventoryForLoanItem(LoanItem item, String warehouseId, int quantityChange) {
        Optional<Inventory> inventoryOptional = inventoryService.getInventory(item.getArticleId(), warehouseId);
        Inventory inventory = inventoryOptional.orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

        int newStock = inventory.getStock() + quantityChange;
        if (newStock < 0) {
            throw new InsufficientStockException("Not enough stock for article " + item.getArticleId());
        }

        inventoryService.editInventory(item.getArticleId(), warehouseId, newStock);
    }
}
