package com.vtduarte.pizzahub.mapper;

import com.vtduarte.pizzahub.database.model.ClienteEntity;
import com.vtduarte.pizzahub.database.model.ItemPedidoEntity;
import com.vtduarte.pizzahub.database.model.PedidoEntity;
import com.vtduarte.pizzahub.database.model.StatusEvent;
import com.vtduarte.pizzahub.dto.response.ClienteResponseDTO;
import com.vtduarte.pizzahub.dto.response.ItemPedidoResponseDTO;
import com.vtduarte.pizzahub.dto.response.PedidoResponseDTO;
import com.vtduarte.pizzahub.dto.response.StatusEventResponseDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponseDTO toResponse(PedidoEntity pedido) {

        if (pedido == null) return null;

        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(pedido.getId());
        dto.setDataPedido(
                LocalDateTime.parse(pedido.getDataPedido()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
        );
        dto.setStatus(pedido.getStatus());
        dto.setTempoEstimado(pedido.getTempoEstimado());

        dto.setValorPedido(pedido.getValorTotal());
        dto.setValorEntrega(pedido.getValorEntrega());
        dto.setValorTotal(pedido.getValorTotal());

        dto.setCliente(mapCliente(pedido.getCliente()));
        dto.setItens(mapItens(pedido.getItens()));
        dto.setTimeline(mapTimeline(pedido.getTimeline()));

        return dto;
    }

    private static ClienteResponseDTO mapCliente(ClienteEntity cliente) {

        if (cliente == null) return null;

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());

        return dto;
    }

    private static List<ItemPedidoResponseDTO> mapItens(List<ItemPedidoEntity> itens) {

        if (itens == null || itens.isEmpty()) {
            return Collections.emptyList();
        }

        return itens.stream()
                .map(item -> {
                    ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();

                    dto.setNomePizza(item.getPizza().getNome());
                    dto.setTamanhoPizza(item.getTamanhoPizza());
                    dto.setQuantidade(item.getQuantidade());
                    dto.setPrecoUnitario(item.getPrecoUnitario());
                    dto.setSubtotal(item.getSubtotal());
                    dto.setObservacao(item.getObservacao());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    private static List<StatusEventResponseDTO> mapTimeline(List<StatusEvent> timeline) {

        if (timeline == null || timeline.isEmpty()) {
            return Collections.emptyList();
        }

        return timeline.stream()
                .map(event -> {
                    StatusEventResponseDTO dto = new StatusEventResponseDTO();

                    dto.setStatusAntigo(event.getStatusAntigo());
                    dto.setStatusNovo(event.getStatusNovo());
                    dto.setHorario(event.getHorario());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
