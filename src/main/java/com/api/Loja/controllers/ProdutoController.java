package com.api.loja.controllers;

import com.api.loja.dtos.ProdutoDto;
import com.api.loja.models.ProdutoModel;
import com.api.loja.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")

public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ProdutoDto dto) {
        ProdutoModel produtoSalvo = produtoService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoModel>> listar() {
        List<ProdutoModel> produtos = produtoService.listar();
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @PathVariable UUID id,
            @RequestBody @Valid ProdutoDto dto) {

        try {
            ProdutoModel produtoAtualizado = produtoService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar: " + e.getMessage());
        }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<?> apagar(@PathVariable(value = "id") UUID id) {
        try {
            produtoService.apagar(id);
            return ResponseEntity.ok("Produto apagado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao apagar: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") UUID id) {
        try {
            ProdutoModel produtoEncontrado = produtoService.buscar(id);
            return ResponseEntity.ok(produtoEncontrado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nomeBusca) {
        try {
            List<ProdutoModel> produtosEncontrados = produtoService.buscarPorNome(nomeBusca);
            return ResponseEntity.ok(produtosEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar por nome: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-descricao")
    public ResponseEntity<?> buscarPorDescricao(@RequestParam String descricaoBusca) {
        try {
            List<ProdutoModel> produtosEncontrados = produtoService.buscarPorDescricao(descricaoBusca);
            return ResponseEntity.ok(produtosEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar por descricao: " + e.getMessage());
        }
    }
}