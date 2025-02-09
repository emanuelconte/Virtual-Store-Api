package com.lojavirtual.api.service;

import com.lojavirtual.api.model.Usuario;
import com.lojavirtual.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Usuario> autenticarUsuario(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> passwordEncoder.matches(senha, usuario.getSenha()));
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
}