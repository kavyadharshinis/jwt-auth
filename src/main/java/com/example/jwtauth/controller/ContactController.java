package com.example.jwtauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "false")
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    public String sendContactMail(@RequestBody Map<String, String> payload) {
        String firstName = payload.getOrDefault("firstName", "");
        String lastName = payload.getOrDefault("lastName", "");
        String email = payload.getOrDefault("email", "");
        String message = payload.getOrDefault("message", "");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("ornaloan.team@gmail.com");
        mailMessage.setSubject("New Contact Form Submission from " + firstName + " " + lastName);
        mailMessage.setText(
                "Name: " + firstName + " " + lastName + "\n" +
                        "Email: " + email + "\n\n" +
                        "Message:\n" + message
        );

        try {
            mailSender.send(mailMessage);
            return "Message sent successfully!";
        } catch (Exception e) {
            // Print error to console
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return "Failed to send message. Please try again later.";
        }
    }
}