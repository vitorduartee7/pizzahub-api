package com.vtduarte.pizzahub.service;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import com.vtduarte.pizzahub.database.model.*;
import com.vtduarte.pizzahub.database.repository.ClienteRepository;
import com.vtduarte.pizzahub.database.repository.ItemPedidoRepository;
import com.vtduarte.pizzahub.database.repository.PedidoRepository;
import com.vtduarte.pizzahub.database.repository.PizzaRepository;
import com.vtduarte.pizzahub.dto.ItemPedidoRequestDTO;
import com.vtduarte.pizzahub.dto.PedidoRequestDTO;
import com.vtduarte.pizzahub.exceptions.BusinessException;
import com.vtduarte.pizzahub.exceptions.ResourceNotFoundException;
import com.vtduarte.pizzahub.utils.PizzaPrecoUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PizzaRepository pizzaRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    // CREATE
    @Transactional
    public PedidoEntity criarPedido(PedidoRequestDTO dto) {

        // Buscar Cliente e Validar Cliente
        ClienteEntity cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não Encontrado"));

        // Criar Pedido
        PedidoEntity pedido = PedidoEntity.builder()
                .cliente(cliente)
                .status(StatusPedidoEnum.CRIADO)
                .build();

        List<ItemPedidoEntity> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // Processar Pedido
        for (ItemPedidoRequestDTO itemDTO : dto.getItens()) {

            // Buscar Pizza
            PizzaEntity pizza = pizzaRepository.findById(itemDTO.getPizzaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada"));

            // Calcular Preço
            BigDecimal precoUnitario = PizzaPrecoUtils.aplicarTamanho(
                    pizza.getPreco(),
                    itemDTO.getTamanho()
            );

            // Adicionar Preço ao Total
            BigDecimal subtotal = precoUnitario
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));

            // Criar Item
            ItemPedidoEntity item = ItemPedidoEntity.builder()
                    .pedido(pedido)
                    .pizza(pizza)
                    .tamanhoPizza(itemDTO.getTamanho())
                    .quantidade(itemDTO.getQuantidade())
                    .observacao(itemDTO.getObservacao())
                    .precoUnitario(precoUnitario)
                    .subtotal(subtotal)
                    .build();

            // Adicionar Item a Lista de Itens
            itens.add(item);

            total = total.add(subtotal);
        }

        // Finalizar Pedido
        pedido.setItens(itens);
        pedido.setValorTotal(total);

        // Salvar Pedido
        pedido = pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itens);

        return pedido;
    }

    // READ ALL
    public List<PedidoEntity> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // READ BY ID
    public PedidoEntity buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    // UPDATE
    @Transactional
    public PedidoEntity atualizarPedido(Long pedidoId, StatusPedidoEnum novoStatus) {

        // Buscar Pedido
        PedidoEntity pedido = buscarPedidoPorId(pedidoId);

        // Status Atual
        StatusPedidoEnum statusAntigo = pedido.getStatus();

        // Validar Transição
        if (!statusAntigo.podeIrPara(novoStatus)) {
            throw new BusinessException(
                    "Transição inválida: " + statusAntigo + " → " + novoStatus
            );
        }

        // Atualizar Log Status
        StatusEvent statusEvent = StatusEvent.builder()
                .statusAntigo(statusAntigo)
                .statusNovo(novoStatus)
                .pedido(pedido)
                .build();

        // Garantir Criação do StatusEvents
        if (pedido.getStatusEvents() == null) {
            pedido.setStatusEvents(new ArrayList<>());
        }

        // Adicionar no Histórico
        pedido.getStatusEvents().add(statusEvent);

        return pedido;
    }

    // DELETE
    @Transactional
    public void deletarPedido(Long pedidoId) {

        // Buscar Pedido
        PedidoEntity pedido = buscarPedidoPorId(pedidoId);

        // Deletar Pedido
        pedidoRepository.delete(pedido);
    }
}
