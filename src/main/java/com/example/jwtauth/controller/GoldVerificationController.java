package com.example.jwtauth.controller;

import com.example.jwtauth.model.GoldDepositRequest;
import com.example.jwtauth.service.GoldVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/deposit")
public class GoldVerificationController {

    @Autowired
    private GoldVerificationService goldService;

    @GetMapping("/pending")
    public ResponseEntity<List<GoldDepositRequest>> viewPendingRequests() {
        return ResponseEntity.ok(goldService.getPendingRequests());
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<GoldDepositRequest> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(goldService.verifyRequest(id, status.toUpperCase()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoldDepositRequest> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(goldService.getById(id));
    }
}
