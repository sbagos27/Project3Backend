package com.example.Project3Backend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Project3Backend.Entities.AppUser;
import com.example.Project3Backend.Services.AppUserService;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService userService;

    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");

            AppUser newUser = userService.createUser(username, password, email);

            return ResponseEntity.ok(Map.of(
                    "message", "User created successfully",
                    "userId", newUser.getId(),
                    "username", newUser.getUsername()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            AppUser user = userService.authenticateUser(username, password);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "userId", user.getId(),
                    "username", user.getUsername()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{userId}/username")
    public ResponseEntity<Map<String, Object>> updateUsername(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        try {
            String newUsername = request.get("username");

            AppUser updatedUser = userService.updateUsername(userId, newUsername);

            return ResponseEntity.ok(Map.of(
                    "message", "Username updated successfully",
                    "userId", updatedUser.getId(),
                    "username", updatedUser.getUsername()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}