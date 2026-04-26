package com.vtduarte.pizzahub.dto.response;

import com.vtduarte.pizzahub.database.enums.TamanhoPizzaEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoResponseDTO {

    private String nomePizza;

    private TamanhoPizzaEnum tamanhoPizza;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;

    private String observacao;
}
