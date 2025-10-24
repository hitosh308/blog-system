package com.example.blogsystem.service;

import com.example.blogsystem.entity.Article;
import com.example.blogsystem.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAllByOrderByPublishedAtDesc();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public Article create(Article article) {
        article.setPublishedAt(LocalDateTime.now());
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(updatedArticle.getTitle());
                    article.setContent(updatedArticle.getContent());
                    if (article.getPublishedAt() == null) {
                        article.setPublishedAt(LocalDateTime.now());
                    }
                    return articleRepository.save(article);
                })
                .orElseThrow(() -> new IllegalArgumentException("Article not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
