package com.IMRequest.IMRequest.controller;

import com.IMRequest.IMRequest.model.entities.Loan;
import com.IMRequest.IMRequest.model.responses.EnrichedLoan;
import com.IMRequest.IMRequest.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // 1. Create a new loan
    @PostMapping("/createLoan")
    public ResponseEntity<?> createLoan(@RequestBody Loan loan) {
        try {
            Loan createdLoan = loanService.createLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. Update the status of one or several items of a given loan to completed (isActive = false)
    @PostMapping("/updateLoanItemsToCompleted/{loanId}")
    public ResponseEntity<?> updateLoanItemsToCompleted(@PathVariable String loanId, @RequestBody List<String> articleIds) {
        try {
            Loan updatedLoan = loanService.updateLoanItemsToCompleted(loanId, articleIds);
            return ResponseEntity.ok(updatedLoan);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Complete all items in the loan and deactivate the loan
    @PostMapping("/completeAllLoanItems/{loanId}")
    public ResponseEntity<?> completeAllLoanItems(@PathVariable String loanId) {
        try {
            Loan completedLoan = loanService.completeAllLoanItems(loanId);
            return ResponseEntity.ok(completedLoan);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4. Get a loan by ID (enriched)
    @GetMapping("/getLoanById/{loanId}")
    public ResponseEntity<?> getEnrichedLoanById(@PathVariable String loanId) {
        try {
            EnrichedLoan enrichedLoan = loanService.getEnrichedLoanById(loanId);
            return ResponseEntity.ok(enrichedLoan);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }
    }

    // 5. Get all loans (enriched)
    @GetMapping("/getAllLoans")
    public ResponseEntity<?> getAllEnrichedLoans() {
        try {
            List<EnrichedLoan> enrichedLoans = loanService.getAllEnrichedLoans();
            return enrichedLoans.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No loans found")
                    : ResponseEntity.ok(enrichedLoans);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
