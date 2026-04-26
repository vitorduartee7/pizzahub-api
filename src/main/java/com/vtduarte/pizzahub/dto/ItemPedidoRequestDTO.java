package com.vtduarte.pizzahub.dto;

import com.vtduarte.pizzahub.database.enums.TamanhoPizzaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemPedidoRequestDTO {

    @NotNull(message = "Id da Pizza é obrigatório")
    private Long pizzaId;

    @NotNull(message = "Tamanho é obrigatório")
    private TamanhoPizzaEnum tamanho;

    @NotNull(message = "Quantidade é obrigatório")
    private Integer quantidade;

    private String observacao;
}
