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


@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final LojaRepository lojaRepository;


    public ProdutoController(ProdutoService produtoService,
                             LojaRepository lojaRepository) {
        this.produtoService = produtoService;
        this.lojaRepository = lojaRepository;

    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(
            @RequestBody @Valid ProdutoDto dto) {
        Optional<LojaModel> loja = lojaRepository.findById(dto.getLojaId());
        if (!loja.isPresent()) {
            return ResponseEntity.badRequest().body(
                    "Loja não existe: " + dto.getLojaId());
        }
        //Cria o modelo do protudo e associar DTO com MODEL
        ProdutoModel produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(dto, produtoModel, "id", "lojaModel");
        produtoModel.setLojaModel(loja.get()); //associar

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoService.salvar(produtoModel));
    }

    @GetMapping("/listar")
    public List<ProdutoModel> listar() {
        return produtoService.listar();
    }



    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid ProdutoDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            Optional<LojaModel> loja = lojaRepository.findById(dto.getLojaId());
            if (!loja.isPresent()) {
                return ResponseEntity.badRequest().body("Loja não encontrada");
            }
            var produto = produtoService.findById(id);
            if(!produto.isPresent()){
                return ResponseEntity.badRequest().body("Produto não encontrado");
            }
            ProdutoModel produtoModel = new ProdutoModel();
            BeanUtils.copyProperties(dto, produtoModel, "lojaModel");
            produtoModel.setId(id);
            produtoModel.setLojaModel(loja.get());
            return ResponseEntity.ok(produtoService.salvar(produtoModel));

        } catch (Exception e) {
            //Retorna eror 500 com a mensagem de erro para o front
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: "+ e.getMessage());
        }
    }

    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok(
                    "Produto apagado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Deu ruim: " +e.getMessage()
            );
        }
    }

    @GetMapping("/buscar")
    public List<ProdutoModel> buscar(
            @RequestParam String nomeBusca
    ){
        return produtoService.buscarPorNome(nomeBusca);
    }




}