package com.lojavirtual.api.repository;

import com.lojavirtual.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r = :role")
    List<Usuario> findByRole(@Param("role") String role);
}