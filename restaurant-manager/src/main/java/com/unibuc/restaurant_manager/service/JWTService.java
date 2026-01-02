package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.exception.UnauthorizedAccessException;
import com.unibuc.restaurant_manager.model.User;
import com.unibuc.restaurant_manager.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public final class JWTService {

    private final String secretKey;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public JWTService(@Value("${security.jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;

        // Admin Token Generation
        System.out.println("Admin Token: " + getToken(("admin")));
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getToken(String id) {
        return Jwts
                .builder()
                .claim("userId", id)
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUserId(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", String.class);
    }

    public static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void checkAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String userId) {
            if (userId.equals("admin")) {
                return;
            }
        }

        throw new UnauthorizedAccessException();
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String userId) {
            Optional<User> user = userRepository.findById(Integer.parseInt(userId));
            if (user.isEmpty()) throw new UnauthorizedAccessException();
            return user.get();
        }

        throw new UnauthorizedAccessException();
    }

//    public static User getAuthorizedUser(Role role) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof String userId) {
//            User user = userRepository.findByIdAndRole(userId, role);
//            if (user != null) return user;
//        }
//
//        throw new UnauthorizedAccessException();
//    }

    public static boolean isPasswordValid(String providedPassword, String actualPassword) {
        return passwordEncoder.matches(providedPassword, actualPassword);
    }

}
