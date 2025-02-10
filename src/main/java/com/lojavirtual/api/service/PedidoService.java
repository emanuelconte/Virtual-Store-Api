package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ItemPedidoDTO;
import com.lojavirtual.api.dto.ItemPedidoRequestDTO;
import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public PedidoDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = toEntity(pedidoRequestDTO);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return toDTO(pedidoSalvo);
    }

    public Page<PedidoDTO> listarPedidos(PageRequest pageRequest) {
        Page<Pedido> pedidos = pedidoRepository.findAll(pageRequest);
        return pedidos.map(this::toDTO);
    }

    public PedidoDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
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

    private Pedido toEntity(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoRequestDTO.getClienteId());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        List<ItemPedido> itens = pedidoRequestDTO.getItens().stream()
                .map(this::toItemPedido)
                .collect(Collectors.toList());

        pedido.setItens(itens);
        return pedido;
    }

    private ItemPedido toItemPedido(ItemPedidoRequestDTO itemRequestDTO) {
        ItemPedido item = new ItemPedido();
        Produto produto = produtoRepository.findById(itemRequestDTO.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

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
        pedidoDTO.setStatus(pedido.getStatus());

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
        itemDTO.setPrecoUnitario(BigDecimal.valueOf(item.getPrecoUnitario()));
        itemDTO.setQuantidade(item.getQuantidade());
        return itemDTO;
    }
}