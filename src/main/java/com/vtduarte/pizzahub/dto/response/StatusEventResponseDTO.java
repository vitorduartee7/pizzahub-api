package com.vtduarte.pizzahub.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StatusEventResponseDTO {

    private StatusPedidoEnum statusAntigo;

    private StatusPedidoEnum statusNovo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime horario;
}
