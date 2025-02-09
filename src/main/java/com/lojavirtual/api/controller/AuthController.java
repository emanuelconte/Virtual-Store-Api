package com.lojavirtual.api.controller;

import com.lojavirtual.api.model.Usuario;
import com.lojavirtual.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario credenciais) {
        return authService.autenticarUsuario(credenciais.getEmail(), credenciais.getSenha())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = authService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }
}