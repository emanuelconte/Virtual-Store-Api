package com.lojavirtual.api.service;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.exception.RecursoNaoEncontradoException;
import com.lojavirtual.api.exception.ValidacaoException;
import com.lojavirtual.api.model.Produto;
import com.lojavirtual.api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarProduto_DeveRetornarProdutoDTO_QuandoDadosValidos() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto Teste");
        requestDTO.setPreco(100.0);
        requestDTO.setDescricao("Descrição Teste");
        requestDTO.setCategoria("Categoria Teste");
        requestDTO.setEstoque(10);

        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome("Produto Teste");
        produtoSalvo.setPreco(100.0);
        produtoSalvo.setDescricao("Descrição Teste");
        produtoSalvo.setCategoria("Categoria Teste");
        produtoSalvo.setEstoque(10);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        ProdutoDTO result = produtoService.cadastrarProduto(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Produto Teste", result.getNome());
        assertEquals(100.0, result.getPreco());
        assertEquals("Descrição Teste", result.getDescricao());
        assertEquals("Categoria Teste", result.getCategoria());
        assertEquals(10, result.getEstoque());

        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void cadastrarProduto_DeveLancarExcecao_QuandoNomeInvalido() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("");
        requestDTO.setPreco(100.0);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            produtoService.cadastrarProduto(requestDTO);
        });

        assertEquals("O nome do produto é obrigatório.", exception.getMessage());
    }

    @Test
    void listarProdutos_DeveRetornarListaDeProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        List<ProdutoDTO> result = produtoService.listarProdutos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getNome());
        assertEquals("Produto 2", result.get(1).getNome());

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void buscarProdutoPorId_DeveRetornarProdutoDTO_QuandoIdExistente() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setDescricao("Descrição Teste");
        produto.setCategoria("Categoria Teste");
        produto.setEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO result = produtoService.buscarProdutoPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Produto Teste", result.getNome());
        assertEquals(100.0, result.getPreco());
        assertEquals("Descrição Teste", result.getDescricao());
        assertEquals("Categoria Teste", result.getCategoria());
        assertEquals(10, result.getEstoque());

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarProdutoPorId_DeveLancarExcecao_QuandoIdInexistente() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarProdutoPorId(1L);
        });

        assertEquals("Produto não encontrado com o ID: 1", exception.getMessage());

        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarProduto_DeveRetornarProdutoDTO_QuandoDadosValidos() {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Produto Atualizado");
        requestDTO.setPreco(200.0);
        requestDTO.setDescricao("Descrição Atualizada");
        requestDTO.setCategoria("Categoria Atualizada");
        requestDTO.setEstoque(20);

        Produto produtoExistente = new Produto();
        produtoExistente.setId(1L);
        produtoExistente.setNome("Produto Original");
        produtoExistente.setPreco(100.0);
        produtoExistente.setDescricao("Descrição Original");
        produtoExistente.setCategoria("Categoria Original");
        produtoExistente.setEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoExistente);

        ProdutoDTO result = produtoService.atualizarProduto(1L, requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Produto Atualizado", result.getNome());
        assertEquals(200.0, result.getPreco());
        assertEquals("Descrição Atualizada", result.getDescricao());
        assertEquals("Categoria Atualizada", result.getCategoria());
        assertEquals(20, result.getEstoque());

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void excluirProduto_DeveExcluirProduto_QuandoIdExistente() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        produtoService.excluirProduto(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void excluirProduto_DeveLancarExcecao_QuandoIdInexistente() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.excluirProduto(1L);
        });

        assertEquals("Produto não encontrado com o ID: 1", exception.getMessage());

        verify(produtoRepository, times(1)).existsById(1L);
    }

    @Test
    void listarProdutosPorCategoria_DeveRetornarListaDeProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setCategoria("Categoria Teste");

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setCategoria("Categoria Teste");

        when(produtoRepository.findByCategoria("Categoria Teste")).thenReturn(Arrays.asList(produto1, produto2));

        List<ProdutoDTO> result = produtoService.listarProdutosPorCategoria("Categoria Teste");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getNome());
        assertEquals("Produto 2", result.get(1).getNome());

        verify(produtoRepository, times(1)).findByCategoria("Categoria Teste");
    }

    @Test
    void listarProdutosPorCategoria_DeveLancarExcecao_QuandoCategoriaInvalida() {
        when(produtoRepository.findByCategoria("Categoria Inexistente")).thenReturn(Arrays.asList());

        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.listarProdutosPorCategoria("Categoria Inexistente");
        });

        assertEquals("Nenhum produto encontrado para a categoria: Categoria Inexistente", exception.getMessage());

        verify(produtoRepository, times(1)).findByCategoria("Categoria Inexistente");
    }

    @Test
    void listarProdutosPorFaixaDePreco_DeveRetornarListaDeProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(150.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setPreco(250.0);

        when(produtoRepository.findByPrecoBetween(100.0, 300.0)).thenReturn(Arrays.asList(produto1, produto2));

        List<ProdutoDTO> result = produtoService.listarProdutosPorFaixaDePreco(100.0, 300.0);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getNome());
        assertEquals("Produto 2", result.get(1).getNome());

        verify(produtoRepository, times(1)).findByPrecoBetween(100.0, 300.0);
    }

    @Test
    void listarProdutosPorFaixaDePreco_DeveLancarExcecao_QuandoFaixaInvalida() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            produtoService.listarProdutosPorFaixaDePreco(300.0, 100.0);
        });

        assertEquals("A faixa de preço é inválida.", exception.getMessage());
    }
}