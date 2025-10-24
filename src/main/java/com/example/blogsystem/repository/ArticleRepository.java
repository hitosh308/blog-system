package com.example.blogsystem.repository;

import com.example.blogsystem.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByPublishedAtDesc();
}
