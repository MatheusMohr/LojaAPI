package com.api.Loja.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LojaDto {
    @NotBlank(message = "O nome é obrigatório!")
    private String nome;
    private String cnpj;
    @NotNull(message = "O cnpj é obrigatório!")
    @NotBlank(message = "A descrição é obrigatória!")
    private String descricao;
}
