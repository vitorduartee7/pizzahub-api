package com.vtduarte.pizzahub.mapper;

import com.vtduarte.pizzahub.database.model.StatusEvent;
import com.vtduarte.pizzahub.dto.response.StatusEventResponseDTO;

public class StatusEventMapper {

    public static StatusEventResponseDTO toDTO(StatusEvent event) {
        return StatusEventResponseDTO.builder()
                .statusAntigo(event.getStatusAntigo())
                .statusNovo(event.getStatusNovo())
                .horario(event.getHorario())
                .build();
    }
}