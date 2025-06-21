package com.example.jwtauth.controller;

import com.example.jwtauth.model.User;
import com.example.jwtauth.service.AuthService;
import com.example.jwtauth.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        System.out.println("Received registration:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        authService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String input = user.getUsername(); // This field will accept either username or email
        System.out.println("Login request received for: " + input);

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input, user.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        final UserDetails userDetails = authService.loadUserByUsername(input);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername()); // username used for token

        return ResponseEntity.ok(jwt);
    }
}

