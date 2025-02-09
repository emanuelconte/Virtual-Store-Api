package com.lojavirtual.api.controller;

import com.lojavirtual.api.model.Produto;
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
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.cadastrarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = produtoService.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> listarProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.listarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> listarProdutosPorFaixaDePreco(
            @RequestParam Double precoMin,
            @RequestParam Double precoMax) {
        List<Produto> produtos = produtoService.listarProdutosPorFaixaDePreco(precoMin, precoMax);
        return ResponseEntity.ok(produtos);
    }
}