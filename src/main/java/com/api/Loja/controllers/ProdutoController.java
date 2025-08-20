package com.api.Loja.controllers;
import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> salvar(
            @RequestBody @Valid ProdutoDto dto,
            BindingResult bindingResult
    ){
        ProdutoModel salvo = produtoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        List<ProdutoModel> listar = produtoService.localizar();
        return ResponseEntity.ok(listar);
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid ProdutoDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            ProdutoModel produtoEditado = produtoService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoEditado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable(value = "id") UUID id) {
        try {
            produtoService.remover(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> buscar(@RequestParam String nomeBusca) {
        List<ProdutoModel> produtos = produtoService.buscaPorNome(nomeBusca);

        if (produtos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nenhum produto encontrado com o nome: " + nomeBusca);
        }

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar-por-descricao")
    public ResponseEntity<?> buscarPorId(@RequestParam String nomeDescricao) {
        List<ProdutoModel> produtos = produtoService.buscaPorDescricao(nomeDescricao);
        if (produtos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nenhum produto encontrado com essa descrição: " + nomeDescricao);
        }
        return ResponseEntity.ok(produtos);
    }
}

