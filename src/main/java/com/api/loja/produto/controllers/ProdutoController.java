package com.api.loja.produto.controllers;

import com.api.loja.loja.models.LojaModel;
import com.api.loja.loja.repository.LojaRepository;
import com.api.loja.produto.dtos.ProdutoDto;
import com.api.loja.produto.models.ProdutoModel;
import com.api.loja.produto.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final LojaRepository lojaRepository;

    public ProdutoController(ProdutoService produtoService, LojaRepository lojaRepository) {
        this.produtoService = produtoService;
        this.lojaRepository = lojaRepository;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ProdutoDto dto) {
        Optional<LojaModel> loja =  lojaRepository.findById(dto.getLojaId());
        if (!loja.isPresent()) {
            return ResponseEntity.badRequest().body("Loja não existe" + dto.getLojaId());
        }

        ProdutoModel produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(dto, produtoModel, "id", "lojaModel");
        produtoModel.setLojaModel(loja.get()); //associar

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.create());
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