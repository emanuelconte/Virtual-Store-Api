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
}