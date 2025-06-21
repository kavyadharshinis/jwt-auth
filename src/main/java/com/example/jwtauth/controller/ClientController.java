package com.example.jwtauth.controller;

import com.example.jwtauth.model.GoldDepositRequest;
import com.example.jwtauth.repository.GoldDepositRequestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasAuthority('ROLE_USER')") // Secures all routes under /client/*
public class ClientController {

    @Autowired
    private GoldDepositRequestRepository requestRepo;

    // ✅ Submit new gold deposit request
    @PostMapping("/deposit")
    public ResponseEntity<?> createDeposit(@RequestBody @Valid GoldDepositRequest request, Authentication authentication) {
        // Set logged-in user as account holder
        String username = authentication.getName(); // comes from JWT
        request.setAccountHolder(username);

        if (request.getStatus() == null) {
            request.setStatus("PENDING");
        }
        request.setCreatedAt(new java.util.Date());

        requestRepo.save(request);
        return ResponseEntity.ok("Gold deposit request submitted successfully.");
    }

    // ✅ View only current client's deposits
    @GetMapping("/deposits")
    public ResponseEntity<?> viewUserDeposits(Authentication authentication) {
        String username = authentication.getName(); // This comes from JWT token
        List<GoldDepositRequest> userDeposits = requestRepo.findByAccountHolder(username);
        return ResponseEntity.ok(userDeposits);
    }

}
