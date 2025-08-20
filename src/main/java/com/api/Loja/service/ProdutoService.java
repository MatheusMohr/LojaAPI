package com.api.Loja.service;
import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoModel create(@Valid ProdutoDto dto) {
        ProdutoModel produto = new ProdutoModel();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        return produtoRepository.save(produto);
    }

    public List<ProdutoModel> localizar() {
        return produtoRepository.findAll();
    }

    public ProdutoModel atualizar(ProdutoDto dto, UUID id) {
        ProdutoModel existente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());

        return produtoRepository.save(existente);
    }

    public void remover(UUID id) {
        ProdutoModel excluir = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepository.deleteById(excluir.getId());
    }

    public List<ProdutoModel> buscaPorNome(String nomeBusca) {
        return produtoRepository.findByNomeContaining(nomeBusca);
    }

    public List<ProdutoModel> buscaPorDescricao(String descricaoBusca) {
        return produtoRepository.findByDescricaoContaining(descricaoBusca);
    }
}

