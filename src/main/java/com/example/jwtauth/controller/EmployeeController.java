package com.example.jwtauth.controller;

import com.example.jwtauth.model.Employee;
import com.example.jwtauth.repository.EmployeeRepository;
import com.example.jwtauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee loginRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // ✅ Get the employee again from DB
        Employee emp = employeeRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // ✅ Use username for token generation
        String token = jwtUtil.generateToken(emp.getUsername());

        return ResponseEntity.ok(token);
    }
}

