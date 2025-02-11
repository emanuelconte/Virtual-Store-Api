package com.lojavirtual.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Representa um pedido na loja virtual")
public class PedidoDTO {

    @Schema(description = "ID do pedido", example = "1")
    private Long id;

    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;

    @Schema(description = "Lista de itens do pedido")
    private List<ItemPedidoDTO> itens;

    @Schema(description = "Data do pedido")
    private LocalDateTime dataPedido;

    @Schema(description = "Status do pedido", example = "PENDENTE")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
