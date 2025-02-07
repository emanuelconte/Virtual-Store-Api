package com.lojavirtual.api.repository;

import com.lojavirtual.api.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    // Busca itens de pedido por produto
    List<ItemPedido> findByProdutoId(Long produtoId);

    // Busca itens de pedido por pedido
    List<ItemPedido> findByPedidoId(Long pedidoId);

    // Soma a quantidade total de um produto vendido
    @Query("SELECT SUM(i.quantidade) FROM ItemPedido i WHERE i.produto.id = :produtoId")
    Integer sumQuantidadeByProdutoId(@Param("produtoId") Long produtoId);
}