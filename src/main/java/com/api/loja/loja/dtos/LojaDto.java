package com.api.loja.loja.dtos;

import com.api.loja.produto.models.ProdutoModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LojaDto {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String descricao;
    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;
    private List<ProdutoModel> produtos = new ArrayList<>();
}
