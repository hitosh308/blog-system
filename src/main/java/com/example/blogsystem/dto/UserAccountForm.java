package com.example.blogsystem.dto;

import com.example.blogsystem.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserAccountForm {

    @NotBlank
    @Size(max = 50)
    private String username;

    private String password;

    @NotNull
    private UserRole role = UserRole.EDITOR;

    private boolean enabled = true;

    public UserAccountForm() {
    }

    public UserAccountForm(String username, String password, UserRole role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
