package com.vtduarte.pizzahub.dto.requests;

import com.vtduarte.pizzahub.database.enums.TamanhoPizzaEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemPedidoRequestDTO {

    @NotNull(message = "Id da Pizza é obrigatório")
    private Long pizzaId;

    @NotNull(message = "Tamanho é obrigatório")
    private TamanhoPizzaEnum tamanho;

    @NotNull(message = "Quantidade é obrigatório")
    private Integer quantidade;

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String observacao;
}
