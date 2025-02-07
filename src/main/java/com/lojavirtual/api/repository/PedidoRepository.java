package com.lojavirtual.api.repository;

import com.lojavirtual.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca pedidos por status
    List<Pedido> findByStatus(Pedido.StatusPedido status);

    // Busca pedidos de um cliente específico
    List<Pedido> findByClienteId(Long clienteId);

    // Busca pedidos feitos em um intervalo de datas
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<Pedido> findByDataPedidoBetween(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
}