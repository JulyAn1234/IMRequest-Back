package com.ecommerceAuth.ecommerceAuth.service;

import com.ecommerceAuth.ecommerceAuth.model.entities.Article;
import com.ecommerceAuth.ecommerceAuth.model.entities.User;
import com.ecommerceAuth.ecommerceAuth.model.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> editArticle(String id,Article updatedArticle) {
        return articleRepository.findById(id).map(article -> {
            article.setActive(updatedArticle.isActive());
            article.setPartida(updatedArticle.getPartida());
            article.setMeasure(updatedArticle.getMeasure());
            article.setDescription(updatedArticle.getDescription());
            article.setName(updatedArticle.getName());

            return articleRepository.save(article);
        });
    }
}
