package com.example.blogsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ArticleForm {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String content;

    public ArticleForm() {
    }

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
