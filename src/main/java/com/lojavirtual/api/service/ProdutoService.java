package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = toEntity(produtoRequestDTO);
        Produto produtoSalvo = produtoRepository.save(produto);
        return toDTO(produtoSalvo);
    }

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return toDTO(produto);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(produtoRequestDTO.getNome());
        produto.setDescricao(produtoRequestDTO.getDescricao());
        produto.setPreco(produtoRequestDTO.getPreco());
        produto.setCategoria(produtoRequestDTO.getCategoria());
        produto.setEstoque(produtoRequestDTO.getEstoque());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return toDTO(produtoAtualizado);
    }

    @Transactional
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoDTO> listarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarProdutosPorFaixaDePreco(Double precoMin, Double precoMax) {
        List<Produto> produtos = produtoRepository.findByPrecoBetween(precoMin, precoMax);
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private Produto toEntity(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.getNome());
        produto.setDescricao(produtoRequestDTO.getDescricao());
        produto.setPreco(produtoRequestDTO.getPreco());
        produto.setCategoria(produtoRequestDTO.getCategoria());
        produto.setEstoque(produtoRequestDTO.getEstoque());
        return produto;
    }

    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setEstoque(produto.getEstoque());
        return produtoDTO;
    }
}