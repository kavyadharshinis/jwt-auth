package com.example.jwtauth.config;

import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.util.JwtUtil;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        System.out.println("🔍 Email from Google: [" + email + "]");

        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Email is null or empty. Redirecting to login error page.");
            response.sendRedirect("http://localhost:5173/login?error=invalid_email");
            return;
        }

        try {
            Optional<User> userOpt = userRepo.findByEmailIgnoreCase(email.trim());
            System.out.println("🔍 User found in DB? " + userOpt.isPresent());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                System.out.println("🔍 Found user: username=" + user.getUsername() + ", role=" + user.getRole() + ", email=" + user.getEmail());

                // Validate user data
                if (user.getUsername() == null || user.getRole() == null) {
                    System.out.println("❌ Invalid user data: username or role is null.");
                    response.sendRedirect("http://localhost:5173/login?error=invalid_user_data");
                    return;
                }

                // Generate JWT
                String jwt = jwtUtil.generateToken(user.getUsername());
                System.out.println("🔐 Generated JWT: " + jwt);

                // Build redirect URL
                String redirectUrl = "http://localhost:5173/oauth2/redirect" +
                        "?token=" + jwt +
                        "&username=" + user.getUsername() +
                        "&role=" + user.getRole();

                System.out.println("🔁 Redirecting to: " + redirectUrl);
                response.sendRedirect(redirectUrl);
            } else {
                // Create new user for OAuth2 login
                System.out.println("❌ No user found for email: [" + email + "]. Creating new user.");
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(oAuth2User.getAttribute("given_name") != null ? oAuth2User.getAttribute("given_name") : email.split("@")[0]);
                newUser.setFirstName(oAuth2User.getAttribute("given_name"));
                newUser.setLastName(oAuth2User.getAttribute("family_name"));
                newUser.setRole("ROLE_USER");
                // Password and phone_number are optional, so leave them null
                userRepo.save(newUser);

                String jwt = jwtUtil.generateToken(newUser.getUsername());
                String redirectUrl = "http://localhost:5173/oauth2/redirect" +
                        "?token=" + jwt +
                        "&username=" + newUser.getUsername() +
                        "&role=" + newUser.getRole();

                System.out.println("🔁 Redirecting to: " + redirectUrl);
                response.sendRedirect(redirectUrl);
            }
        } catch (Exception e) {
            System.out.println("❌ Error in OAuth2SuccessHandler: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("http://localhost:5173/login?error=server_error");
        }
    }
}