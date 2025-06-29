package com.example.jwtauth.config;

import com.example.jwtauth.service.MyUserDetailsService;
import com.example.jwtauth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println("🔒 JwtAuthFilter triggered for: " + request.getRequestURI());

        final String token;
        final String username;

        // CORS preflight OPTIONS request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("🔁 Skipping OPTIONS request");
            filterChain.doFilter(request, response);
            return;
        }

        // Check if header exists and is Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("🚫 No Authorization header or invalid format");
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        try {
            username = jwtUtil.extractUsername(token);
            System.out.println("✅ Extracted username from token: " + username);
        } catch (Exception e) {
            System.out.println("❌ Error extracting username: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // If user not authenticated yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                System.out.println("❌ User not found: " + username);
                filterChain.doFilter(request, response);
                return;
            }

            boolean valid = jwtUtil.isTokenValid(token, userDetails);
            System.out.println("🔍 Is token valid? " + valid);

            if (valid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ SecurityContext updated for user: " + username);
                System.out.println("👮 Authorities: " + userDetails.getAuthorities());
            } else {
                System.out.println("❌ Token is not valid for user: " + username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
