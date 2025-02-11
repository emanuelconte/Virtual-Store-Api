package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.exception.RecursoNaoEncontradoException;
import com.lojavirtual.api.exception.ValidacaoException;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO) {

        if (produtoRequestDTO.getNome() == null || produtoRequestDTO.getNome().trim().isEmpty()) {
            throw new ValidacaoException("O nome do produto é obrigatório.");
        }

        if (produtoRequestDTO.getPreco() == null || produtoRequestDTO.getPreco() <= 0) {
            throw new ValidacaoException("O preço do produto deve ser maior que zero.");
        }

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
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id));
        return toDTO(produto);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id));

        if (produtoRequestDTO.getNome() == null || produtoRequestDTO.getNome().trim().isEmpty()) {
            throw new ValidacaoException("O nome do produto é obrigatório.");
        }

        if (produtoRequestDTO.getPreco() == null || produtoRequestDTO.getPreco() <= 0) {
            throw new ValidacaoException("O preço do produto deve ser maior que zero.");
        }

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
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public List<ProdutoDTO> listarProdutosPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new ValidacaoException("A categoria não pode ser vazia.");
        }

        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        if (produtos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum produto encontrado para a categoria: " + categoria);
        }

        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarProdutosPorFaixaDePreco(Double precoMin, Double precoMax) {
        if (precoMin == null || precoMax == null || precoMin.compareTo(precoMax) > 0) {
            throw new ValidacaoException("A faixa de preço é inválida.");
        }

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