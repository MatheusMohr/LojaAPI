package com.api.loja.loja.repository;

import com.api.loja.loja.models.LojaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LojaRepository extends
        JpaRepository<LojaModel, UUID> {

    List<LojaModel> findByNomeContainingIgnoreCase(String nome);
    List<LojaModel> findByDescricaoContainingIgnoreCase(String descricao);
}
