package com.vtduarte.pizzahub.dto.response;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusEventResponseDTO {

    private StatusPedidoEnum statusAntigo;

    private StatusPedidoEnum statusNovo;

    private LocalDateTime horario;
}
