package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.exception.UnauthorizedAccessException;
import com.unibuc.restaurant_manager.model.Customer;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    private static final String TEST_SECRET = "877577264c51e80972dd54bd1f4b0f6f7a50999b2674d1d14c16169bed2d5fab";

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JWTService jwtService = new JWTService(TEST_SECRET);

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getToken_and_extractUserId_success() {
        String token = jwtService.getToken("42");
        String extracted = jwtService.extractUserId(token);

        assertEquals("42", extracted);
    }

    @Test
    void encryptPassword_and_validate_success() {
        String rawPassword = "myPassword123!";
        String encoded = JWTService.encryptPassword(rawPassword);

        assertNotEquals(rawPassword, encoded);
        assertTrue(JWTService.isPasswordValid(rawPassword, encoded));
    }

    @Test
    void checkAdmin_adminUser_ok() {
        when(authentication.getPrincipal()).thenReturn("admin");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertDoesNotThrow(() -> jwtService.checkAdmin());

        verify(authentication, times(1)).getPrincipal();
    }

    @Test
    void checkAdmin_nonAdmin_throwsException() {
        when(authentication.getPrincipal()).thenReturn("123");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(UnauthorizedAccessException.class, () -> jwtService.checkAdmin());
    }

    @Test
    void checkAdmin_noAuthentication_throwsException() {
        SecurityContextHolder.clearContext();

        assertThrows(UnauthorizedAccessException.class, () -> jwtService.checkAdmin());
    }

    @Test
    void getUser_validUser_success() {
        User mockUser = Customer.builder()
                .id(10)
                .username("customer")
                .password(JWTService.encryptPassword("customer123-PASSWORD"))
                .lastName("Customer-LastName")
                .firstName("Customer-FirstName")
                .email("customer.mail@gmial.com")
                .phoneNumber("0712345678")
                .address("Customer-Address")
                .build();

        when(authentication.getPrincipal()).thenReturn("10");
        when(userRepository.findById(10)).thenReturn(Optional.of(mockUser));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User result = jwtService.getUser();

        assertEquals(10, result.getId());
        assertEquals("customer", result.getUsername());

        verify(authentication, times(1)).getPrincipal();
        verify(userRepository, times(1)).findById(argThat(id -> id.equals(10)));
    }

    @Test
    void getUser_userNotFound_throwsException() {
        when(authentication.getPrincipal()).thenReturn("100");
        when(userRepository.findById(100)).thenReturn(Optional.empty());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(UnauthorizedAccessException.class, () -> jwtService.getUser());

        verify(authentication, times(1)).getPrincipal();
        verify(userRepository, times(1)).findById(argThat(id -> id.equals(100)));
    }

    @Test
    void getUser_noAuthentication_throwsException() {
        SecurityContextHolder.clearContext();

        assertThrows(UnauthorizedAccessException.class, () -> jwtService.getUser());
    }

}
