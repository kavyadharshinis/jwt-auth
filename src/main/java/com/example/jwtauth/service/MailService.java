package com.example.jwtauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Send email when a loan request is accepted
     */
    public void sendLoanApprovalMail(String recipientEmail, String recipientName) {
        try {
            if (recipientEmail == null || recipientEmail.isEmpty()) {
                System.err.println("❌ Email is empty or null");
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Loan Request Approved");
            message.setText("Dear " + recipientName + ",\n\n"
                    + "Your gold loan request has been successfully verified and approved by the bank.\n\n"
                    + "Thank you,\nOrnaLoan Team");
            message.setFrom("ornaloan.team@gmail.com");

            mailSender.send(message);
            System.out.println("✅ Loan approval email sent to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send loan approval email to " + recipientEmail);
            e.printStackTrace();
        }
    }

    /**
     * Optional: Send a basic verification email
     */
    public void sendVerificationEmail(String recipientEmail) {
        try {
            if (recipientEmail == null || recipientEmail.isEmpty()) {
                System.err.println("❌ Email is empty or null");
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Verification Successful");
            message.setText("Your verification has been completed successfully.");
            message.setFrom("ornaloan.team@gmail.com");

            mailSender.send(message);
            System.out.println("✅ Verification email sent to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send verification email to " + recipientEmail);
            e.printStackTrace();
        }
    }
}
