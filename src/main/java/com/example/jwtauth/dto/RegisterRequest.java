package com.example.jwtauth.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dob; // Changed to LocalDate to match User entity
    private String gender;
    private String firstName;
    private String lastName;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDob() { return dob; }
    public void setDob(String dob) {
        if (dob != null && !dob.trim().isEmpty()) {
            try {
                // Parse the date string (expected format: "yyyy-MM-dd")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                this.dob = LocalDate.parse(dob.trim(), formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format for dob. Expected format: yyyy-MM-dd", e);
            }
        } else {
            this.dob = null; // Allow null if dob is optional
        }
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}