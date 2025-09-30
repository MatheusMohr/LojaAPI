package com.api.loja.loja.services;

import com.api.loja.loja.dtos.LojaDto;
import com.api.loja.loja.models.LojaModel;
import com.api.loja.loja.repository.LojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LojaService {
    private LojaRepository lojaRepository;
    public LojaService(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    public LojaModel create(LojaDto dto) {
        LojaModel produto = new LojaModel();
        produto.setNome(dto.getNome());
        produto.setEndereco(dto.getEndereco());
        produto.setCnpj(dto.getCnpj());
        return lojaRepository.save(produto);
    }

    public List<LojaModel> listar() {
        return lojaRepository.findAll();
    }

    public Optional<LojaModel> findById(UUID id) {
        return lojaRepository.findById(id);
    }

    public LojaModel atualizar(LojaDto dto, UUID id){
        LojaModel existente = lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        existente.setNome(dto.getNome());
        existente.setEndereco(dto.getEndereco());
        existente.setCnpj(dto.getCnpj());
        return lojaRepository.save(existente);
    }
    public void deletar(UUID id) {
        LojaModel existente = lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        lojaRepository.deleteById(existente.getId());
    }
    public List<LojaModel> buscarPorNome(String nomeBusca) {
        return    lojaRepository.findByNomeContainingIgnoreCase(nomeBusca);
    }
}