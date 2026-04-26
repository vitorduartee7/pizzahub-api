package com.vtduarte.pizzahub.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PizzaResponseDTO {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal precoBase;

    private boolean disponivel;
}
