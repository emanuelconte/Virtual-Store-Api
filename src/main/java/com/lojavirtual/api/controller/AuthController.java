package com.lojavirtual.api.controller;

import com.lojavirtual.api.model.Usuario;
import com.lojavirtual.api.security.JwtUtil;
import com.lojavirtual.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida e token retornado")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<String> login(@RequestBody Usuario credenciais) {
        return authService.autenticarUsuario(credenciais.getEmail(), credenciais.getSenha())
                .map(usuario -> {
                    String token = JwtUtil.generateToken(usuario.getEmail());
                    return ResponseEntity.ok(token);
                })
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar novo usuário", description = "Cadastra um novo usuário na plataforma")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = authService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }
}