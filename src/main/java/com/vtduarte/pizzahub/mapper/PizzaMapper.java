package com.vtduarte.pizzahub.mapper;

import com.vtduarte.pizzahub.database.model.PizzaEntity;
import com.vtduarte.pizzahub.dto.response.PizzaResponseDTO;

public class PizzaMapper {

    public static PizzaResponseDTO toResponse(PizzaEntity pizza) {

        if (pizza == null) return null;

        PizzaResponseDTO dto = new PizzaResponseDTO();

        dto.setId(pizza.getId());
        dto.setNome(pizza.getNome());
        dto.setDescricao(pizza.getDescricao());
        dto.setPrecoBase(pizza.getPrecoBase());
        dto.setDisponivel(pizza.isDisponivel());

        return dto;
    }
}
