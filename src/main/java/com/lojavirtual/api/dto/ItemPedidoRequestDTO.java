package com.lojavirtual.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa um Request do Item Pedido na loja virtual")
public class ItemPedidoRequestDTO {

    @Schema(description = "ID do produto", example = "1")
    private Long produtoId;

    @Schema(description = "Quantidade do pedido", example = "1")
    private int quantidade;

    public ItemPedidoRequestDTO(Long produtoId, int quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
