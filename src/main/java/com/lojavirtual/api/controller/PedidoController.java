package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoDTO pedidoDTO = pedidoService.criarPedido(pedidoRequestDTO);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PedidoDTO> pedidosDTO = pedidoService.listarPedidos(PageRequest.of(page, size));
        return new ResponseEntity<>(pedidosDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestParam Pedido.StatusPedido novoStatus) {
        PedidoDTO pedidoDTO = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorStatus(@RequestParam Pedido.StatusPedido status) {
        List<PedidoDTO> pedidosDTO = pedidoService.listarPedidosPorStatus(status);
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidosDTO = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidosDTO);
    }
}