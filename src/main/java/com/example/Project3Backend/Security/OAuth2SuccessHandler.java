package com.example.Project3Backend.Security;

import com.example.Project3Backend.Entities.AppUser;
import com.example.Project3Backend.Services.AppUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AppUserService userService;

    public OAuth2SuccessHandler(AppUserService userService) {
        this.userService = userService;
        setDefaultTargetUrl("/api/users/me");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        
        // Determine which provider was used
        String provider = oauth2Token.getAuthorizedClientRegistrationId();
        String providerId;
        String email;
        String username;
        
        if ("github".equals(provider)) {
            // GitHub OAuth2 attributes
            providerId = oauth2User.getAttribute("id").toString();
            email = oauth2User.getAttribute("email");
            if (email == null || email.isEmpty()) {
                // Try to get email from another attribute or API
                email = oauth2User.getAttribute("login") + "@github.user";
            }
            username = oauth2User.getAttribute("login");
            if (username == null || username.isEmpty()) {
                username = oauth2User.getAttribute("name");
                if (username == null || username.isEmpty()) {
                    username = email.split("@")[0];
                }
            }
        } else if ("google".equals(provider)) {
            // Google OAuth2 attributes
            providerId = oauth2User.getAttribute("sub");
            email = oauth2User.getAttribute("email");
            username = oauth2User.getAttribute("name");
            if (username == null || username.isEmpty()) {
                username = oauth2User.getAttribute("given_name");
                if (username == null || username.isEmpty()) {
                    username = email.split("@")[0];
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
        }
        
        // Create or update user in database
        AppUser user = userService.findOrCreateUserFromOAuth(provider, providerId, email, username);
        
        // Store user ID in session or as a custom attribute
        request.getSession().setAttribute("userId", user.getId());
        request.getSession().setAttribute("username", user.getUsername());
        
        // Redirect to a frontend success page or return user info
        // Use the parent class method to handle redirect
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

