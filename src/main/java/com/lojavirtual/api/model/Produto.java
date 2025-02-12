package com.lojavirtual.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private Double preco;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotNull(message = "O estoque é obrigatório")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    private Integer estoque = 0;

    public @NotBlank(message = "O nome é obrigatório") @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "A descrição é obrigatória") @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres") String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotBlank(message = "A descrição é obrigatória") @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres") String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "O preço é obrigatório") @Positive(message = "O preço deve ser positivo") Double preco) {
        this.preco = preco;
    }

    public @NotBlank(message = "A categoria é obrigatória") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotBlank(message = "A categoria é obrigatória") String categoria) {
        this.categoria = categoria;
    }

    public @NotNull(message = "O estoque é obrigatório") @PositiveOrZero(message = "O estoque não pode ser negativo") Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(@NotNull(message = "O estoque é obrigatório") @PositiveOrZero(message = "O estoque não pode ser negativo") Integer estoque) {
        this.estoque = estoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}