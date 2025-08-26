package com.api.loja.controllers;

import com.api.loja.dtos.LojaDto;
import com.api.loja.models.LojaModel;
import com.api.loja.services.LojaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lojas")

public class LojaController {
    private final LojaService LojaService;

    public LojaController(LojaService LojaService) {
        this.LojaService = LojaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid LojaDto dto) {
        LojaModel lojaSalvo = LojaService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(lojaSalvo);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LojaModel>> listar() {
        List<LojaModel> lojas = LojaService.listar();
        return ResponseEntity.ok(lojas);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @PathVariable UUID id,
            @RequestBody @Valid LojaDto dto) {

        try {
            LojaModel lojaAtualizado = LojaService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(lojaAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar: " + e.getMessage());
        }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<?> apagar(@PathVariable(value = "id") UUID id) {
        try {
            LojaService.apagar(id);
            return ResponseEntity.ok("loja apagado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao apagar: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(value = "id") UUID id) {
        try {
            LojaModel lojaEncontrado = LojaService.buscar(id);
            return ResponseEntity.ok(lojaEncontrado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nomeBusca) {
        try {
            List<LojaModel> lojasEncontrados = LojaService.buscarPorNome(nomeBusca);
            return ResponseEntity.ok(lojasEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar por nome: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-descricao")
    public ResponseEntity<?> buscarPorDescricao(@RequestParam String descricaoBusca) {
        try {
            List<LojaModel> lojasEncontrados = LojaService.buscarPorDescricao(descricaoBusca);
            return ResponseEntity.ok(lojasEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar por descricao: " + e.getMessage());
        }
    }
}