package com.api.Loja.service;

import com.api.Loja.dtos.LojaDto;
import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.LojaModel;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.repository.LojaRepository;
import com.api.Loja.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class LojaService {
    private LojaRepository lojaRepository;
    public LojaService(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    public LojaModel create(@Valid LojaDto dto) {
        LojaModel loja = new LojaModel();
        loja.setNome(dto.getNome());
        loja.setCnpj(dto.getDescricao());
        loja.setDescricao(dto.getDescricao());
        return lojaRepository.save(loja);
    }
}
