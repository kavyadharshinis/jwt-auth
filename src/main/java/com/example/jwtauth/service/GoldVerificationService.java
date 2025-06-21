package com.example.jwtauth.service;

import com.example.jwtauth.model.GoldDepositRequest;
import com.example.jwtauth.repository.GoldDepositRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoldVerificationService {

    @Autowired
    private GoldDepositRequestRepository depositRepo;

    public List<GoldDepositRequest> getPendingRequests() {
        return depositRepo.findByStatus("PENDING");
    }

    public GoldDepositRequest verifyRequest(Long id, String newStatus) {
        GoldDepositRequest request = depositRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit Request not found"));
        request.setStatus(newStatus); // "VERIFIED", "REJECTED", etc.
        return depositRepo.save(request);
    }

    public GoldDepositRequest getById(Long id) {
        return depositRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
