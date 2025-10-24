package com.example.blogsystem.controller;

import com.example.blogsystem.entity.Article;
import com.example.blogsystem.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReaderController {

    private final ArticleService articleService;

    public ReaderController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"/", "/articles"})
    public String listArticles(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found: " + id));
        model.addAttribute("article", article);
        return "article";
    }
}
