package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoDTO produtoDTO = produtoService.cadastrarProduto(produtoRequestDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutos();
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoDTO produtoDTO = produtoService.atualizarProduto(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtosDTO);
    }
    
    @GetMapping("/preco")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorFaixaDePreco(
            @RequestParam Double precoMin,
            @RequestParam Double precoMax) {
        List<ProdutoDTO> produtosDTO = produtoService.listarProdutosPorFaixaDePreco(precoMin, precoMax);
        return ResponseEntity.ok(produtosDTO);
    }
}