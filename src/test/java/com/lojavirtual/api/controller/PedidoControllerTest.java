package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarPedido() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        PedidoDTO pedidoDTO = new PedidoDTO();

        when(pedidoService.criarPedido(any(PedidoRequestDTO.class))).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = pedidoController.criarPedido(pedidoRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
        verify(pedidoService, times(1)).criarPedido(pedidoRequestDTO);
    }

    @Test
    void listarPedidos() {
        Page<PedidoDTO> pedidosDTO = new PageImpl<>(Collections.singletonList(new PedidoDTO()));

        when(pedidoService.listarPedidos(any(PageRequest.class))).thenReturn(pedidosDTO);

        ResponseEntity<Page<PedidoDTO>> response = pedidoController.listarPedidos(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTO, response.getBody());
        verify(pedidoService, times(1)).listarPedidos(PageRequest.of(0, 10));
    }

    @Test
    void buscarPedidoPorId() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Long id = 1L;

        when(pedidoService.buscarPedidoPorId(id)).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = pedidoController.buscarPedidoPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
        verify(pedidoService, times(1)).buscarPedidoPorId(id);
    }

    @Test
    void atualizarStatusPedido() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Long id = 1L;
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.EM_PROCESSAMENTO;

        when(pedidoService.atualizarStatusPedido(id, novoStatus)).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = pedidoController.atualizarStatusPedido(id, novoStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoDTO, response.getBody());
        verify(pedidoService, times(1)).atualizarStatusPedido(id, novoStatus);
    }

    @Test
    void excluirPedido() {
        Long id = 1L;

        doNothing().when(pedidoService).excluirPedido(id);

        ResponseEntity<Void> response = pedidoController.excluirPedido(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).excluirPedido(id);
    }

    @Test
    void listarPedidosPorStatus() {
        Pedido.StatusPedido status = Pedido.StatusPedido.EM_PROCESSAMENTO;
        List<PedidoDTO> pedidosDTO = Collections.singletonList(new PedidoDTO());

        when(pedidoService.listarPedidosPorStatus(status)).thenReturn(pedidosDTO);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTO, response.getBody());
        verify(pedidoService, times(1)).listarPedidosPorStatus(status);
    }

    @Test
    void listarPedidosPorCliente() {
        Long clienteId = 1L;
        List<PedidoDTO> pedidosDTO = Collections.singletonList(new PedidoDTO());

        when(pedidoService.listarPedidosPorCliente(clienteId)).thenReturn(pedidosDTO);

        ResponseEntity<List<PedidoDTO>> response = pedidoController.listarPedidosPorCliente(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTO, response.getBody());
        verify(pedidoService, times(1)).listarPedidosPorCliente(clienteId);
    }
}