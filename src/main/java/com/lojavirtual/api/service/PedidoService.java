package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ItemPedidoDTO;
import com.lojavirtual.api.dto.ItemPedidoRequestDTO;
import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
import com.lojavirtual.api.exception.EstoqueInsuficienteException;
import com.lojavirtual.api.exception.RecursoNaoEncontradoException;
import com.lojavirtual.api.model.ItemPedido;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.model.Pedido.StatusPedido;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.PedidoRepository;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public PedidoDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Map<Long, Produto> produtosRecuperados = new HashMap<>();

        for (ItemPedidoRequestDTO item : pedidoRequestDTO.getItens()) {
            Produto produto = produtosRecuperados.computeIfAbsent(item.getProdutoId(), id ->
                    produtoRepository.findById(id)
                            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id))
            );

            if (produto.getEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        Pedido pedido = toEntity(pedidoRequestDTO, produtosRecuperados);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return toDTO(pedidoSalvo);
    }

    public Page<PedidoDTO> listarPedidos(PageRequest pageRequest) {
        Page<Pedido> pedidos = pedidoRepository.findAll(pageRequest);
        return pedidos.map(this::toDTO);
    }

    public PedidoDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: " + id));
        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: " + id));
        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return toDTO(pedidoAtualizado);
    }

    @Transactional
    public void excluirPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<PedidoDTO> listarPedidosPorStatus(StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.findByStatus(status);
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<PedidoDTO> listarPedidosPorCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private Pedido toEntity(PedidoRequestDTO pedidoRequestDTO, Map<Long, Produto> produtosRecuperados) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoRequestDTO.getClienteId());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        List<ItemPedido> itens = pedidoRequestDTO.getItens().stream()
                .map(itemRequestDTO -> toItemPedido(itemRequestDTO, produtosRecuperados))
                .collect(Collectors.toList());

        pedido.setItens(itens);
        return pedido;
    }

    private ItemPedido toItemPedido(ItemPedidoRequestDTO itemRequestDTO, Map<Long, Produto> produtosRecuperados) {
        ItemPedido item = new ItemPedido();
        Produto produto = produtosRecuperados.get(itemRequestDTO.getProdutoId());

        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + itemRequestDTO.getProdutoId());
        }

        item.setProduto(produto);
        item.setQuantidade(itemRequestDTO.getQuantidade());
        item.setPrecoUnitario(produto.getPreco());
        return item;
    }

    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setClienteId(pedido.getClienteId());
        pedidoDTO.setDataPedido(pedido.getDataPedido());
        pedidoDTO.setStatus(String.valueOf(pedido.getStatus()));

        List<ItemPedidoDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemPedidoDTO)
                .collect(Collectors.toList());

        pedidoDTO.setItens(itensDTO);
        return pedidoDTO;
    }

    private ItemPedidoDTO toItemPedidoDTO(ItemPedido item) {
        ItemPedidoDTO itemDTO = new ItemPedidoDTO();
        itemDTO.setId(item.getId());
        itemDTO.setProdutoId(item.getProduto().getId());
        itemDTO.setProdutoNome(item.getProduto().getNome());
        itemDTO.setPrecoUnitario(item.getPrecoUnitario());
        itemDTO.setQuantidade(item.getQuantidade());
        return itemDTO;
    }
}