package com.api.loja.services;

import com.api.loja.dtos.ProdutoDto;
import com.api.loja.models.ProdutoModel;
import com.api.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoModel create(ProdutoDto dto) {
        ProdutoModel produto = new ProdutoModel();
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setNome(dto.getNome());

        return produtoRepository.save(produto);
    }

    public List<ProdutoModel> listar() {
        return produtoRepository.findAll();
    }

    public ProdutoModel atualizar(ProdutoDto dto, UUID id) {
        ProdutoModel produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        produtoExistente.setNome(dto.getNome());
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setDescricao(dto.getDescricao());
        return produtoRepository.save(produtoExistente);
    }

    public void apagar(UUID id) {
        ProdutoModel produtoParaDeletar = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        produtoRepository.delete(produtoParaDeletar);
    }

    public ProdutoModel buscar(UUID id) {
        ProdutoModel produtoEncontrado = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        return produtoEncontrado;
    }

    public List<ProdutoModel> buscarPorNome(String nomeBusca) {
        List<ProdutoModel> produtos = produtoRepository.findByNomeContainingIgnoreCase(nomeBusca);
        if (produtos.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com o nome informado: " + nomeBusca);
        }
        return produtos;
    }

    public List<ProdutoModel> buscarPorDescricao(String descricaoBusca) {
        List<ProdutoModel> produtos = produtoRepository.findByDescricaoContainingIgnoreCase(descricaoBusca);
        if (produtos.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com a descrição informada: " + descricaoBusca);
        }
        return produtos;
    }
}