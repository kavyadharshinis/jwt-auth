package com.example.jwtauth.config;

import com.example.jwtauth.config.JwtAuthFilter;
import com.example.jwtauth.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (for login)
                        .requestMatchers("/auth/**", "/employee/login", "/client/login").permitAll()

                        // Employee access
                        .requestMatchers("/admin/**", "/bank/**").hasAuthority("ROLE_EMPLOYEE")

                        // Client access
                        .requestMatchers("/client/**").hasAuthority("ROLE_USER")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**", "/employee/login").permitAll()
//                        .requestMatchers("/admin/**").hasAuthority("ROLE_EMPLOYEE")
//                        .requestMatchers("/bank/deposit/**").hasAuthority("ROLE_EMPLOYEE")
//                        .requestMatchers("/client/**").permitAll()
//                        // ✅ Allow public access to both login routes
//                        .anyRequest().authenticated()  // ✅ Everything else requires JWT
//                )
