package com.vtduarte.pizzahub.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "Id do Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Itens é obrigatório")
    private List<ItemPedidoRequestDTO> itens;
}
