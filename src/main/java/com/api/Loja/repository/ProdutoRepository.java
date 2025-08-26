package com.api.loja.repository;

import com.api.loja.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends
        JpaRepository<ProdutoModel, UUID> {

    List<ProdutoModel> findByNomeContainingIgnoreCase(String nome);
    List<ProdutoModel> findByDescricaoContainingIgnoreCase(String descricao);
}
