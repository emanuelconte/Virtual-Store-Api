package com.lojavirtual.api.service;

import com.lojavirtual.api.model.ItemPedido;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.PedidoRepository;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            item.setPedido(pedido);
        }
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido atualizarStatusPedido(Long id, Pedido.StatusPedido novoStatus) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(novoStatus);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public void excluirPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> listarPedidosPorStatus(Pedido.StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
}