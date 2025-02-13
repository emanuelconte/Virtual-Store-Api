package com.lojavirtual.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateToken() {
        String email = "test@gmail.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void isValidToken_ValidToken() {
        String token = "valid.token.here";
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@gmail.com");
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() + 86400000L));

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenReturn(mock(io.jsonwebtoken.Jws.class));
        when(jwtParser.parseClaimsJws(token).getBody()).thenReturn(claims);

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            boolean isValid = jwtUtil.isValidToken(token);
            assertTrue(isValid);
        }
    }

    @Test
    void isValidToken_InvalidToken() {
        String token = "invalid.token.here";

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenThrow(new RuntimeException("Invalid token"));

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            boolean isValid = jwtUtil.isValidToken(token);
            assertFalse(isValid);
        }
    }

    @Test
    void getUsername_ValidToken() {
        String token = "valid.token.here";
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@gmail.com");

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenReturn(mock(io.jsonwebtoken.Jws.class));
        when(jwtParser.parseClaimsJws(token).getBody()).thenReturn(claims);

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            String username = jwtUtil.getUsername(token);
            assertEquals("test@gmail.com", username);
        }
    }

    @Test
    void getUsername_InvalidToken() {
        String token = "invalid.token.here";

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenThrow(new RuntimeException("Invalid token"));

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            String username = jwtUtil.getUsername(token);
            assertNull(username);
        }
    }

    @Test
    void getClaims_ValidToken() {
        String token = "valid.token.here";
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@gmail.com");

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenReturn(mock(io.jsonwebtoken.Jws.class));
        when(jwtParser.parseClaimsJws(token).getBody()).thenReturn(claims);

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            Claims result = jwtUtil.getClaims(token);
            assertNotNull(result);
            assertEquals("test@gmail.com", result.getSubject());
        }
    }

    @Test
    void getClaims_InvalidToken() {
        String token = "invalid.token.here";

        JwtParser jwtParser = mock(JwtParser.class);
        when(jwtParser.setSigningKey(any(byte[].class))).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(token)).thenThrow(new RuntimeException("Invalid token"));

        try (MockedStatic<Jwts> mockedJwts = Mockito.mockStatic(Jwts.class)) {
            mockedJwts.when(Jwts::parser).thenReturn(jwtParser);

            Claims result = jwtUtil.getClaims(token);
            assertNull(result);
        }
    }
}