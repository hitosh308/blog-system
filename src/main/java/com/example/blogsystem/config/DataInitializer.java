package com.example.blogsystem.config;

import com.example.blogsystem.entity.Article;
import com.example.blogsystem.entity.UserRole;
import com.example.blogsystem.service.ArticleService;
import com.example.blogsystem.service.UserAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(UserAccountService userAccountService, ArticleService articleService) {
        return args -> {
            if (userAccountService.findAll().isEmpty()) {
                userAccountService.create("admin", "password", UserRole.ADMIN, true);
            }

            if (articleService.findAll().isEmpty()) {
                articleService.create(new Article("ようこそ", "ブログシステムへようこそ。"));
                articleService.create(new Article("Spring Boot", "Spring Bootで簡単なブログを作成しました。"));
            }
        };
    }
}
