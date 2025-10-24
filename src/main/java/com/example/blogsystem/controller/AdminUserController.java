package com.example.blogsystem.controller;

import com.example.blogsystem.dto.UserAccountForm;
import com.example.blogsystem.entity.UserAccount;
import com.example.blogsystem.entity.UserRole;
import com.example.blogsystem.service.UserAccountService;
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
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserAccountService userAccountService;

    public AdminUserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userAccountService.findAll());
        return "admin/users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userAccountForm", new UserAccountForm());
        model.addAttribute("roles", UserRole.values());
        return "admin/users/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("userAccountForm") UserAccountForm form,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "admin/users/form";
        }
        if (form.getPassword() == null || form.getPassword().isBlank()) {
            bindingResult.rejectValue("password", "password.required", "パスワードを入力してください");
            model.addAttribute("roles", UserRole.values());
            return "admin/users/form";
        }
        userAccountService.create(form.getUsername(), form.getPassword(), form.getRole(), form.isEnabled());
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        UserAccount account = userAccountService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        model.addAttribute("userId", id);
        model.addAttribute("userAccountForm", new UserAccountForm(
                account.getUsername(), "", account.getRole(), account.isEnabled()));
        model.addAttribute("roles", UserRole.values());
        return "admin/users/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("userAccountForm") UserAccountForm form,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", id);
            model.addAttribute("roles", UserRole.values());
            return "admin/users/form";
        }
        userAccountService.update(id, form.getUsername(), form.getPassword(), form.getRole(), form.isEnabled());
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userAccountService.delete(id);
        return "redirect:/admin/users";
    }
}
