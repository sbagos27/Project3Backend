package com.example.Project3Backend.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Project3Backend.Entities.AppUser;
import com.example.Project3Backend.Repositories.AppUserRepository;
import java.util.Optional;
import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository userRepo;

    public AppUserService(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public AppUser createUser(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        Optional<AppUser> existingUser = userRepo.findByUsernameIgnoreCase(username.trim());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Optional<AppUser> existingEmail = userRepo.findByEmail(email.trim());
        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(username.trim());
        newUser.setPassword(password.trim());
        newUser.setEmail(email.trim());

        return userRepo.save(newUser);
    }

    @Transactional
    public AppUser authenticateUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Optional<AppUser> user = userRepo.findByUsernameIgnoreCase(username.trim());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        AppUser foundUser = user.get();
        if (!foundUser.getPassword().equals(password.trim())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return foundUser;
    }

    @Transactional
    public AppUser updateUsername(Long userId, String newUsername) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        Optional<AppUser> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        // Check if the new username is already taken by another user
        Optional<AppUser> existingUser = userRepo.findByUsernameIgnoreCase(newUsername.trim());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser foundUser = user.get();
        foundUser.setUsername(newUsername.trim());

        return userRepo.save(foundUser);
    }

    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }
}