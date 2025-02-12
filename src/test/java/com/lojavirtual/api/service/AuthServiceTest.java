package com.lojavirtual.api.service;

import com.lojavirtual.api.model.Usuario;
import com.lojavirtual.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAutenticarUsuario_Sucesso() {
        String email = "usuario@teste.com";
        String senha = "senha123";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(true);

        Optional<Usuario> resultado = authService.autenticarUsuario(email, senha);

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(senha, usuario.getSenha());
    }

    @Test
    void testAutenticarUsuario_SenhaIncorreta() {
        String email = "usuario@teste.com";
        String senha = "senhaErrada";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(false);

        Optional<Usuario> resultado = authService.autenticarUsuario(email, senha);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(senha, usuario.getSenha());
    }

    @Test
    void testAutenticarUsuario_UsuarioNaoEncontrado() {
        String email = "naoexiste@teste.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = authService.autenticarUsuario(email, senha);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testCadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@teste.com");
        usuario.setSenha("senha123");

        when(passwordEncoder.encode("senha123")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = authService.cadastrarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("senhaCriptografada", resultado.getSenha());
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(usuarioRepository, times(1)).save(usuario);
    }
}