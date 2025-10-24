package com.example.blogsystem.controller;

import com.example.blogsystem.dto.ArticleForm;
import com.example.blogsystem.entity.Article;
import com.example.blogsystem.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {

    private final ArticleService articleService;

    public AdminArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "admin/articles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        return "admin/articles/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("articleForm") ArticleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/articles/form";
        }
        articleService.create(new Article(form.getTitle(), form.getContent()));
        return "redirect:/admin/articles";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found: " + id));
        model.addAttribute("articleId", id);
        model.addAttribute("articleForm", new ArticleForm(article.getTitle(), article.getContent()));
        return "admin/articles/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("articleForm") ArticleForm form,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", id);
            return "admin/articles/form";
        }
        articleService.update(id, new Article(form.getTitle(), form.getContent()));
        return "redirect:/admin/articles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/admin/articles";
    }
}
