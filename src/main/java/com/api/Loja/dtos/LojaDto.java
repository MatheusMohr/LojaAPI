package com.api.loja.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LojaDto {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;

    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;
}
