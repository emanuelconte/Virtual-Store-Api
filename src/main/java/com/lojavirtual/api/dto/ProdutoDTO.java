package com.lojavirtual.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa um produto na loja virtual")
public class ProdutoDTO {

    @Schema(description = "ID do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Banana")
    private String nome;

    @Schema(description = "Descricao do produto", example = "Este produto e uma fruta")
    private String descricao;

    @Schema(description = "Preco do produto", example = "2.50")
    private Double preco;

    @Schema(description = "Categoria do produto", example = "Alimento")
    private String categoria;

    @Schema(description = "Estoque do produto", example = "10")
    private int estoque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
