package com.lojavirtual.api.repository;

import com.lojavirtual.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca produtos por categoria
    List<Produto> findByCategoria(String categoria);

    // Busca produtos com preço dentro de uma faixa
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax")
    List<Produto> findByPrecoBetween(@Param("precoMin") Double precoMin, @Param("precoMax") Double precoMax);

    // Busca produtos com estoque maior que um valor específico
    List<Produto> findByEstoqueGreaterThan(Integer estoque);
}