package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.PedidoDTO;
import com.lojavirtual.api.dto.PedidoRequestDTO;
import com.lojavirtual.api.model.Pedido;
import com.lojavirtual.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido na loja virtual")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoDTO pedidoDTO = pedidoService.criarPedido(pedidoRequestDTO);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos paginados", description = "Retorna uma lista paginada de pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PedidoDTO> pedidosDTO = pedidoService.listarPedidos(PageRequest.of(page, size));
        return new ResponseEntity<>(pedidosDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido existente")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestParam Pedido.StatusPedido novoStatus) {
        PedidoDTO pedidoDTO = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido", description = "Remove um pedido do sistema pelo ID")
    @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(summary = "Listar pedidos por status", description = "Retorna uma lista de pedidos filtrados pelo status")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorStatus(@RequestParam Pedido.StatusPedido status) {
        List<PedidoDTO> pedidosDTO = pedidoService.listarPedidosPorStatus(status);
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Retorna todos os pedidos de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidosDTO = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidosDTO);
    }
}