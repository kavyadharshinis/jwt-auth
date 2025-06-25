package com.example.jwtauth.service;

import com.example.jwtauth.model.User;
import com.example.jwtauth.model.Employee;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        // First check in users (clients) by username or email
        Optional<User> userOpt = userRepo.findByEmail(input)
                .or(() -> userRepo.findByUsername(input));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
            );
        }

        // If not found, check in employees
        Optional<Employee> empOpt = employeeRepo.findByUsername(input);
        if (empOpt.isPresent()) {
            Employee emp = empOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    emp.getUsername(),
                    emp.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(emp.getRole()))
            );
        }

        // If no match found, throw error
        throw new UsernameNotFoundException("User or Employee not found with input: " + input);
    }
}
