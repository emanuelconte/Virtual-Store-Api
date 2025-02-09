package com.lojavirtual.api.service;

import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setCategoria(produtoAtualizado.getCategoria());
                    produto.setEstoque(produtoAtualizado.getEstoque());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> listarProdutosPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> listarProdutosPorFaixaDePreco(Double precoMin, Double precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }
}