package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo produto", description = "Cria um novo produto na loja virtual")
    @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Conflito: produto já existe")
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoDTO produtoDTO = produtoService.cadastrarProduto(produtoRequestDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutos();
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico pelo ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produtoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualiza os dados de um produto existente")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoDTO produtoDTO = produtoService.atualizarProduto(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um produto", description = "Remove um produto pelo ID")
    @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar produtos por categoria", description = "Retorna uma lista de produtos filtrados por categoria")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/preco")
    @Operation(summary = "Listar produtos por faixa de preço", description = "Retorna produtos dentro de uma faixa de preço especificada")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorFaixaDePreco(
            @RequestParam Double precoMin,
            @RequestParam Double precoMax) {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutosPorFaixaDePreco(precoMin, precoMax);
        return ResponseEntity.ok(produtosDTO);
    }
}