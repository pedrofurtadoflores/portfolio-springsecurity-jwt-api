package br.com.pedrofurtadoflores.springsecurityjwtapi.config.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private final String base64Secret = "dGhpc2lzbXlqd3RzZWNyZXRrZXlmb3Jwb3J0Zm9saW8="; 
    
    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "jwtSecret", base64Secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L); // 1 hora
    }

    @Test
    void shouldGenerateAndValidateToken() {
        String email = "user@example.com";
        String token = jwtService.generateToken(email);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, email));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        String email = "user@example.com";
        String token = jwtService.generateToken(email);

        String extracted = jwtService.extractUsername(token);
        assertEquals(email, extracted);
    }

    @Test
    void shouldExtractExpirationCorrectly() {
        String token = jwtService.generateToken("user@example.com");

        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void shouldGenerateTokenWithExtraClaims() {
        String token = jwtService.generateToken("user@example.com", Map.of("role", "admin"));

        Claims claims = ReflectionTestUtils.invokeMethod(jwtService, "extractAllClaims", token);
        assertEquals("admin", claims.get("role"));
    }

    @Test
    void shouldInvalidateExpiredToken() {
        // Define uma expiração curtíssima (1ms) e gera o token
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1L);

        String token = jwtService.generateToken("expired@example.com");

        // Aguarda um tempo mínimo para garantir expiração
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Thread was interrupted");
        }

        boolean valid = jwtService.isTokenValid(token, "expired@example.com");
        assertFalse(valid, "Token should be invalid due to expiration");
    }

}
