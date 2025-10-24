package com.example.blogsystem.service;

import com.example.blogsystem.entity.UserAccount;
import com.example.blogsystem.entity.UserRole;
import com.example.blogsystem.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    @Transactional
    public UserAccount create(String username, String rawPassword, UserRole role, boolean enabled) {
        if (userAccountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        UserAccount account = new UserAccount();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(rawPassword));
        account.setRole(role);
        account.setEnabled(enabled);
        return userAccountRepository.save(account);
    }

    @Transactional
    public UserAccount update(Long id, String username, String rawPassword, UserRole role, boolean enabled) {
        return userAccountRepository.findById(id)
                .map(account -> {
                    if (!account.getUsername().equals(username) && userAccountRepository.existsByUsername(username)) {
                        throw new IllegalArgumentException("Username already exists: " + username);
                    }
                    account.setUsername(username);
                    if (rawPassword != null && !rawPassword.isBlank()) {
                        account.setPassword(passwordEncoder.encode(rawPassword));
                    }
                    account.setRole(role);
                    account.setEnabled(enabled);
                    return userAccountRepository.save(account);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        userAccountRepository.deleteById(id);
    }
}
