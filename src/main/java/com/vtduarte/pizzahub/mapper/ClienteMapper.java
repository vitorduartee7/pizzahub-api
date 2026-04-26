package com.vtduarte.pizzahub.mapper;

import com.vtduarte.pizzahub.database.model.ClienteEntity;
import com.vtduarte.pizzahub.dto.response.ClienteResponseDTO;

public class ClienteMapper {

    public static ClienteResponseDTO toResponse(ClienteEntity cliente) {

        if (cliente == null) return null;

        ClienteResponseDTO dto = new ClienteResponseDTO();

        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());

        return dto;
    }
}
