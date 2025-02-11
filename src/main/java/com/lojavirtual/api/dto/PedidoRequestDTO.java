package com.lojavirtual.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Representa um Request do pedido na loja virtual")
public class PedidoRequestDTO {

    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;

    @Schema(description = "Lista de Item Pedido")
    private List<ItemPedidoRequestDTO> itens;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}