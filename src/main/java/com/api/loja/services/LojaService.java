package com.api.loja.services;

import com.api.loja.dtos.LojaDto;
import com.api.loja.models.LojaModel;
import com.api.loja.repository.LojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LojaService {
    private LojaRepository LojaRepository;

    public LojaService(LojaRepository LojaRepository) {
        this.LojaRepository = LojaRepository;
    }

    public LojaModel create(LojaDto dto) {
        LojaModel loja = new LojaModel();
        loja.setDescricao(dto.getDescricao());
        loja.setCnpj(dto.getCnpj());
        loja.setNome(dto.getNome());

        return LojaRepository.save(loja);
    }

    public List<LojaModel> listar() {
        return LojaRepository.findAll();
    }

    public LojaModel atualizar(LojaDto dto, UUID id) {
        LojaModel lojaExistente = LojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("loja não encontrado com o ID: " + id));
        lojaExistente.setNome(dto.getNome());
        lojaExistente.setCnpj(dto.getCnpj());
        lojaExistente.setDescricao(dto.getDescricao());
        return LojaRepository.save(lojaExistente);
    }

    public void apagar(UUID id) {
        LojaModel lojaParaDeletar = LojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("loja não encontrado com o ID: " + id));

        LojaRepository.delete(lojaParaDeletar);
    }

    public LojaModel buscar(UUID id) {
        LojaModel lojaEncontrado = LojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("loja não encontrado com o ID: " + id));

        return lojaEncontrado;
    }

    public List<LojaModel> buscarPorNome(String nomeBusca) {
        List<LojaModel> lojas = LojaRepository.findByNomeContainingIgnoreCase(nomeBusca);
        if (lojas.isEmpty()) {
            throw new RuntimeException("loja não encontrado com o nome informado: " + nomeBusca);
        }
        return lojas;
    }

    public List<LojaModel> buscarPorDescricao(String descricaoBusca) {
        List<LojaModel> lojas = LojaRepository.findByDescricaoContainingIgnoreCase(descricaoBusca);
        if (lojas.isEmpty()) {
            throw new RuntimeException("loja não encontrado com a descrição informada: " + descricaoBusca);
        }
        return lojas;
    }
}