package com.example.jwtauth.controller;

import com.example.jwtauth.model.GoldDepositRequest;
import com.example.jwtauth.repository.GoldDepositRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")  // 🔐 Ensures only employees can access
public class AdminController {

    @Autowired
    private GoldDepositRequestRepository depositRepo;

    // ✅ 1. Admin dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("✅ Welcome to the Admin Dashboard!");
    }

    // ✅ 2. View all pending gold deposit requests
    @GetMapping("/deposits/pending")
    public ResponseEntity<List<GoldDepositRequest>> viewPendingDeposits() {
        List<GoldDepositRequest> pending = depositRepo.findByStatus("PENDING");
        return ResponseEntity.ok(pending);
    }

    // ✅ 3. Approve or reject a deposit request by ID
    @PutMapping("/deposits/verify/{id}")
    public ResponseEntity<String> verifyDepositStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        GoldDepositRequest request = depositRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit request not found with ID: " + id));

        if (!status.equalsIgnoreCase("APPROVED") && !status.equalsIgnoreCase("REJECTED")) {
            return ResponseEntity.badRequest().body("❌ Status must be either APPROVED or REJECTED.");
        }

        request.setStatus(status.toUpperCase());
        depositRepo.save(request);

        return ResponseEntity.ok("✅ Request ID " + id + " updated to status: " + status.toUpperCase());
    }
    @PutMapping("/deposits/submit/{id}")
    public ResponseEntity<String> markAsPhysicallySubmitted(@PathVariable Long id) {
        GoldDepositRequest request = depositRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getStatus().equals("APPROVED")) {
            return ResponseEntity.badRequest()
                    .body("❌ Only approved requests can be marked as physically submitted.");
        }

        request.setStatus("PHYSICALLY_SUBMITTED");
        depositRepo.save(request);

        return ResponseEntity.ok("✅ Request ID " + id + " marked as PHYSICALLY_SUBMITTED.");
    }

}
