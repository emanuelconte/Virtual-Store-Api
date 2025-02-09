package com.lojavirtual.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O produto é obrigatório")
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço unitário deve ser positivo")
    private Double precoUnitario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "O produto é obrigatório") Produto getProduto() {
        return produto;
    }

    public void setProduto(@NotNull(message = "O produto é obrigatório") Produto produto) {
        this.produto = produto;
    }

    public @NotNull(message = "A quantidade é obrigatória") @Positive(message = "A quantidade deve ser positiva") Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@NotNull(message = "A quantidade é obrigatória") @Positive(message = "A quantidade deve ser positiva") Integer quantidade) {
        this.quantidade = quantidade;
    }

    public @NotNull(message = "O preço unitário é obrigatório") @Positive(message = "O preço unitário deve ser positivo") Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(@NotNull(message = "O preço unitário é obrigatório") @Positive(message = "O preço unitário deve ser positivo") Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}