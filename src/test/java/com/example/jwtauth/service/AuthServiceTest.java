package com.example.jwtauth.service;

import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.EmployeeRepository;
import com.example.jwtauth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepository userRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    // ✅ Test 1: Successful registration
    @Test
    void testRegisterEncodesPasswordAndSavesUser() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("plainPassword");

        when(userRepo.findByUsername("john")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        authService.register(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepo, times(1)).save(user);
    }

    // ❌ Test 2: Duplicate user registration
    @Test
    void testRegisterFailsIfUserAlreadyExists() {
        User user = new User();
        user.setUsername("john");

        when(userRepo.findByUsername("john")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(user);
        });

        assertEquals("User already exists", exception.getMessage());
        verify(userRepo, never()).save(any());
    }

    // ✅ Test 3: Successful authentication
    @Test
    void testAuthenticateSuccess() {
        String username = "john";
        String password = "1234";

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        authService.authenticate(username, password);

        verify(authManager, times(1)).authenticate(token);
    }

    // ❌ Test 4: Failed authentication
    @Test
    void testAuthenticateFailure() {
        String username = "wronguser";
        String password = "wrongpass";

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authManager).authenticate(token);

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(username, password);
        });

        assertEquals("Bad credentials", exception.getMessage());
    }
}
