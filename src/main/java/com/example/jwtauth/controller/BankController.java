package com.example.jwtauth.controller;

import com.example.jwtauth.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private MailService mailService;

    // For approving a loan request
    @PostMapping("/accept-request")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public String acceptRequest(@RequestParam String email, @RequestParam String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 Authenticated as: " + auth.getName());
        System.out.println("🛡️ Roles: " + auth.getAuthorities());

        mailService.sendLoanApprovalMail(email, name);
        return "Loan approval email sent to " + email;
    }



    // Optional: reuse if needed for other purposes
    @PostMapping("/send-verification")
    public String sendVerification(@RequestBody String email) {
        mailService.sendVerificationEmail(email);
        return "Verification email sent to " + email;
    }
}
