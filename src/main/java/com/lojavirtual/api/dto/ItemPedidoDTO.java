package com.lojavirtual.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa um Item pedido na loja virtual")
public class ItemPedidoDTO {

    @Schema(description = "ID do pedido", example = "1")
    private Long id;

    @Schema(description = "ID do produto", example = "1")
    private Long produtoId;

    @Schema(description = "Nome do produto", example = "Banana")
    private String produtoNome;

    @Schema(description = "Preco do produto", example = "2.50")
    private Double precoUnitario;

    @Schema(description = "Quantidade de produtos", example = "5")
    private int quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
