package com.lojavirtual.api.controller;

import com.lojavirtual.api.dto.ProdutoDTO;
import com.lojavirtual.api.dto.ProdutoRequestDTO;
import com.lojavirtual.api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarProduto() {
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();
        ProdutoDTO produtoDTO = new ProdutoDTO();

        when(produtoService.cadastrarProduto(any(ProdutoRequestDTO.class))).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.cadastrarProduto(produtoRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoDTO, response.getBody());
        verify(produtoService, times(1)).cadastrarProduto(produtoRequestDTO);
    }

    @Test
    void listarProdutos() {
        List<ProdutoDTO> produtosDTO = Collections.singletonList(new ProdutoDTO());

        when(produtoService.listarProdutos()).thenReturn(produtosDTO);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTO, response.getBody());
        verify(produtoService, times(1)).listarProdutos();
    }

    @Test
    void buscarProdutoPorId() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Long id = 1L;

        when(produtoService.buscarProdutoPorId(id)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.buscarProdutoPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoDTO, response.getBody());
        verify(produtoService, times(1)).buscarProdutoPorId(id);
    }

    @Test
    void atualizarProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        Long id = 1L;
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();

        when(produtoService.atualizarProduto(id, produtoRequestDTO)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.atualizarProduto(id, produtoRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoDTO, response.getBody());
        verify(produtoService, times(1)).atualizarProduto(id, produtoRequestDTO);
    }

    @Test
    void excluirProduto() {
        Long id = 1L;

        doNothing().when(produtoService).excluirProduto(id);

        ResponseEntity<Void> response = produtoController.excluirProduto(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoService, times(1)).excluirProduto(id);
    }

    @Test
    void listarProdutosPorCategoria() {
        String categoria = "Eletrônicos";
        List<ProdutoDTO> produtosDTO = Collections.singletonList(new ProdutoDTO());

        when(produtoService.listarProdutosPorCategoria(categoria)).thenReturn(produtosDTO);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarProdutosPorCategoria(categoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTO, response.getBody());
        verify(produtoService, times(1)).listarProdutosPorCategoria(categoria);
    }

    @Test
    void listarProdutosPorFaixaDePreco() {
        Double precoMin = 100.0;
        Double precoMax = 500.0;
        List<ProdutoDTO> produtosDTO = Collections.singletonList(new ProdutoDTO());

        when(produtoService.listarProdutosPorFaixaDePreco(precoMin, precoMax)).thenReturn(produtosDTO);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarProdutosPorFaixaDePreco(precoMin, precoMax);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTO, response.getBody());
        verify(produtoService, times(1)).listarProdutosPorFaixaDePreco(precoMin, precoMax);
    }
}