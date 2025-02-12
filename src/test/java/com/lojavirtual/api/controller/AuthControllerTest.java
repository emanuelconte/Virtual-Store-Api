package com.lojavirtual.api.controller;

import com.lojavirtual.api.model.Usuario;
import com.lojavirtual.api.security.JwtUtil;
import com.lojavirtual.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_DeveRetornarUsuario_QuandoDadosValidos() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("senha123");

        when(authService.cadastrarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = authController.registrarUsuario(usuario);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("teste@gmail.com", response.getBody().getEmail());
        assertEquals("senha123", response.getBody().getSenha());

        verify(authService, times(1)).cadastrarUsuario(usuario);
    }

    @Test
    void registrarUsuario_DeveRetornar400_QuandoDadosInvalidos() {
        Usuario usuario = new Usuario();
        usuario.setEmail(""); // Email inválido
        usuario.setSenha(""); // Senha inválida

        when(authService.cadastrarUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        assertThrows(IllegalArgumentException.class, () -> {
            authController.registrarUsuario(usuario);
        });

        verify(authService, times(1)).cadastrarUsuario(usuario);
    }

    @Test
    void login_DeveRetornarToken_QuandoCredenciaisValidas() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("senha123");

        when(authService.autenticarUsuario(anyString(), anyString()))
                .thenReturn(Optional.of(usuario));
        when(jwtUtil.generateToken(anyString())).thenReturn("token-gerado");

        ResponseEntity<String> response = authController.login(usuario);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token-gerado", response.getBody());

        verify(authService, times(1)).autenticarUsuario("teste@gmail.com", "senha123");
        verify(jwtUtil, times(1)).generateToken("teste@gmail.com");
    }

    @Test
    void login_DeveRetornar401_QuandoCredenciaisInvalidas() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("senha123");

        when(authService.autenticarUsuario(anyString(), anyString()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.login(usuario);

        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue());

        verify(authService, times(1)).autenticarUsuario("teste@gmail.com", "senha123");
        verify(jwtUtil, never()).generateToken(anyString());
    }
}