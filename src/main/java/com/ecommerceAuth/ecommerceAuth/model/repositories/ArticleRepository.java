package com.ecommerceAuth.ecommerceAuth.model.repositories;

import com.ecommerceAuth.ecommerceAuth.model.entities.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
