package com.lojavirtual.api.repository;

import com.lojavirtual.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(Pedido.StatusPedido status);

    List<Pedido> findByClienteId(Long clienteId);

}