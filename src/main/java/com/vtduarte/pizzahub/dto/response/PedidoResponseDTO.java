package com.vtduarte.pizzahub.dto.response;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {

    private Long id;

    private LocalDateTime dataPedido;

    private StatusPedidoEnum status;

    private Integer tempoEstimado;

    private ClienteResponseDTO cliente;

    private List<ItemPedidoResponseDTO> itens;

    private BigDecimal valorPedido;

    private BigDecimal valorEntrega;

    private BigDecimal valorTotal;

    private List<StatusEventResponseDTO> timeline;
}
