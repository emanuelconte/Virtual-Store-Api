package com.lojavirtual.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Representa uma Request do Produto na loja virtual")
public class ProdutoRequestDTO {

    @Schema(description = "Nome do produto", example = "Banana")
    private String nome;

    @Schema(description = "Descricao do produto", example = "Este produto e uma fruta")
    private String descricao;

    @Schema(description = "Preco do produto", example = "2.50")
    private Double preco;

    @Schema(description = "Categoria do produto", example = "Alimento")
    private String categoria;

    @Schema(description = "Estoque do produto", example = "1")
    private int estoque;

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

    public @NotNull(message = "O preço é obrigatório") @Positive(message = "O preço deve ser positivo") Double getPreco() {
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
