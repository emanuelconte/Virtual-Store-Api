package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ItemPedidoRequestDTO;
import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
import com.lojavirtual.api.exception.EstoqueInsuficienteException;
import com.lojavirtual.api.exception.RecursoNaoEncontradoException;
import com.lojavirtual.api.model.ItemPedido;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.PedidoRepository;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPedido_Sucesso() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setClienteId(1L);
        pedidoRequestDTO.setItens(Collections.singletonList(new ItemPedidoRequestDTO(1L, 2)));

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PedidoDTO resultado = pedidoService.criarPedido(pedidoRequestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getClienteId());
        assertEquals("PENDENTE", resultado.getStatus());
        assertEquals(1, resultado.getItens().size());

        verify(produtoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testCriarPedido_ProdutoNaoEncontrado() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setClienteId(1L);
        pedidoRequestDTO.setItens(Collections.singletonList(new ItemPedidoRequestDTO(1L, 2)));

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));
        verify(produtoRepository, times(1)).findById(1L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void testCriarPedido_EstoqueInsuficiente() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setClienteId(1L);
        pedidoRequestDTO.setItens(Collections.singletonList(new ItemPedidoRequestDTO(1L, 15)));

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(EstoqueInsuficienteException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));
        verify(produtoRepository, times(1)).findById(1L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void testListarPedidos() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1L);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(Pedido.StatusPedido.PENDENTE);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(100.0);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setEstoque(10);
        itemPedido.setProduto(produto);

        pedido.setItens(Collections.singletonList(itemPedido));

        Page<Pedido> pedidosPage = new PageImpl<>(Collections.singletonList(pedido));
        when(pedidoRepository.findAll(pageRequest)).thenReturn(pedidosPage);

        Page<PedidoDTO> resultado = pedidoService.listarPedidos(pageRequest);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(1L, resultado.getContent().get(0).getId());
        assertEquals(1, resultado.getContent().get(0).getItens().size());
        verify(pedidoRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testBuscarPedidoPorId_Sucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1L);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(Pedido.StatusPedido.PENDENTE);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(100.0);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setEstoque(10);
        itemPedido.setProduto(produto);

        pedido.setItens(Collections.singletonList(itemPedido));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        PedidoDTO resultado = pedidoService.buscarPedidoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getClienteId());
        assertEquals("PENDENTE", resultado.getStatus());
        assertEquals(1, resultado.getItens().size());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPedidoPorId_NaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> pedidoService.buscarPedidoPorId(1L));
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void testAtualizarStatusPedido_Sucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1L);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(Pedido.StatusPedido.PENDENTE);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(100.0);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setEstoque(10);
        itemPedido.setProduto(produto);

        pedido.setItens(Collections.singletonList(itemPedido));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PedidoDTO resultado = pedidoService.atualizarStatusPedido(1L, Pedido.StatusPedido.CONCLUIDO);

        assertNotNull(resultado);
        assertEquals("CONCLUIDO", resultado.getStatus());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testExcluirPedido() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.excluirPedido(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }
}