package com.example.jwtauth.controller;

import com.example.jwtauth.dto.RegisterRequest;
import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.EmployeeRepository;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.service.AuthService;
import com.example.jwtauth.service.MyUserDetailsService;
import com.example.jwtauth.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.jwtauth.dto.LoginRequest;


import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MyUserDetailsService userDetailsService;



    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("Received registration:");
        System.out.println("Username: " + request.getUsername());
        System.out.println("Email: " + request.getEmail());

        // Map DTO to entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // encoding will be done in service
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDob(request.getDob());
        user.setGender(request.getGender());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole("ROLE_USER");

        authService.register(user);

        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User user) {
//        String input = user.getUsername();
//        System.out.println("Login request received for: " + input);
//
//        try {
//            authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(input, user.getPassword())
//            );
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        final UserDetails userDetails = authService.loadUserByUsername(input);
//        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//        return ResponseEntity.ok(jwt);
//    }
@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
    String input = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    System.out.println("💥 Received login from frontend: " + loginRequest.getUsername());

    try {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(input, password)
        );
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(input);
    String token = jwtUtil.generateToken(userDetails.getUsername());
    String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

    return ResponseEntity.ok(Map.of(
            "token", token,
            "username", userDetails.getUsername(),
            "role", role
    ));
}
    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("Test successful!");
    }




}

