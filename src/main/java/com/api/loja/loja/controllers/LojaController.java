package com.api.loja.loja.controllers;

import com.api.loja.loja.dtos.LojaDto;
import com.api.loja.loja.models.LojaModel;
import com.api.loja.loja.services.LojaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/loja")
public class LojaController {
    private final LojaService lojaService;

    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @GetMapping("/listar")
    public List<LojaModel> listar() {
        return lojaService.listar();
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(
            @RequestBody @Valid LojaDto dto) {
        LojaModel produtoSalvo = lojaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                produtoSalvo);
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid LojaDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            LojaModel produtoEditado = lojaService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(produtoEditado);
        } catch (Exception e) {
            //Retorna eror 500 com a mensagem de erro para o front
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: "+ e.getMessage());
        }
    }

    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            lojaService.deletar(id);
            return ResponseEntity.ok(
                    "Produto apagado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Deu ruim: " +e.getMessage()
            );
        }
    }

    @GetMapping("/buscar")
    public List<LojaModel> buscar(
            @RequestParam String nomeBusca
    ){
        return lojaService.buscarPorNome(nomeBusca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaModel> getLojaById(@PathVariable UUID id) {
        Optional<LojaModel> loja = lojaService.findById(id);
        return loja.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}