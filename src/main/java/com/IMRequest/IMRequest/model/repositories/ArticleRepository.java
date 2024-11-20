package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String> {
    Optional<Article> findByIdAndIsActiveTrue(String id); // Get active article by ID
}

