package com.example.jwtauth.repository;

import com.example.jwtauth.model.GoldDepositRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoldDepositRequestRepository extends JpaRepository<GoldDepositRequest, Long> {

    // ✅ For admin to view pending/approved/rejected requests
    List<GoldDepositRequest> findByStatus(String status);

    // ✅ For client to view their own submitted deposits
    List<GoldDepositRequest> findByAccountHolder(String accountHolder);
}

