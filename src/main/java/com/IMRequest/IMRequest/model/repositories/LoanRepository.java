package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Article;
import com.IMRequest.IMRequest.model.entities.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan, String>  {
}
