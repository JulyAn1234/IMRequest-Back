package com.IMRequest.IMRequest.model.repositories;

import com.IMRequest.IMRequest.model.entities.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
