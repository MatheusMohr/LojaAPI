package com.api.Loja.controllers;

import com.api.Loja.dtos.LojaDto;
import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.LojaModel;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.service.LojaService;
import com.api.Loja.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lojas")
public class LojaController {
    private final LojaService lojaService;
    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(
            @RequestBody @Valid LojaDto dto,
            BindingResult bindingResult
    ){
        LojaModel salvo = lojaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

}
