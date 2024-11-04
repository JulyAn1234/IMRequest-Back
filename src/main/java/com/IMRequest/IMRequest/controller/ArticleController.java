package com.IMRequest.IMRequest.controller;

import com.IMRequest.IMRequest.model.entities.Article;
import com.IMRequest.IMRequest.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getAllArticles")
    public ResponseEntity<?> getAllArticles() {
        try{
            List<Article> articleList = articleService.getAllArticles();
            return articleList.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(articleList);
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createArticle")
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        try{
            Article createdArticle = articleService.createArticle(article);
            return ResponseEntity.ok(createdArticle);
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/editArticle/{id}")
    public ResponseEntity<?> editArticle(@PathVariable String id, @RequestBody Article article) {
        try{
            Optional<Article> updatedArticle = articleService.editArticle(id, article);
            return updatedArticle.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
