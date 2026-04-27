package com.vtduarte.pizzahub.service;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import com.vtduarte.pizzahub.database.model.*;
import com.vtduarte.pizzahub.database.repository.ClienteRepository;
import com.vtduarte.pizzahub.database.repository.ItemPedidoRepository;
import com.vtduarte.pizzahub.database.repository.PedidoRepository;
import com.vtduarte.pizzahub.database.repository.PizzaRepository;
import com.vtduarte.pizzahub.dto.requests.ItemPedidoRequestDTO;
import com.vtduarte.pizzahub.dto.requests.PedidoRequestDTO;
import com.vtduarte.pizzahub.dto.response.PedidoResponseDTO;
import com.vtduarte.pizzahub.dto.response.StatusEventResponseDTO;
import com.vtduarte.pizzahub.exceptions.BusinessException;
import com.vtduarte.pizzahub.exceptions.ResourceNotFoundException;
import com.vtduarte.pizzahub.mapper.PedidoMapper;
import com.vtduarte.pizzahub.mapper.StatusEventMapper;
import com.vtduarte.pizzahub.utils.PizzaPrecoUtils;
import com.vtduarte.pizzahub.utils.TempoPedidoUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        // Buscar Cliente e Validar Cliente
        ClienteEntity cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não Encontrado"));

        // Criar Pedido
        PedidoEntity pedido = PedidoEntity.builder()
                .cliente(cliente)
                .status(StatusPedidoEnum.CRIADO)
                .dataPedido(LocalDateTime.now())
                .itens(new ArrayList<>())
                .build();

        if (pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }

        if (pedido.getTimeline() == null) {
            pedido.setTimeline(new ArrayList<>());
        }

        List<ItemPedidoEntity> itens = new ArrayList<>();
        BigDecimal valorPedido = BigDecimal.ZERO;

        // Processar Pedido
        for (ItemPedidoRequestDTO itemDTO : dto.getItens()) {

            // Buscar Pizza
            PizzaEntity pizza = pizzaRepository.findById(itemDTO.getPizzaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada"));

            // Validar Disponibilidade
            if (!pizza.isDisponivel()) {
                throw new BusinessException("Pizza indisponível: " + pizza.getNome());
            }

            // Calcular Preço
            BigDecimal precoUnitario = PizzaPrecoUtils.aplicarTamanho(
                    pizza.getPrecoBase(),
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
            valorPedido = valorPedido.add(subtotal);
        }

        // Finalizar Pedido
        pedido.setItens(itens);
        pedido.setValorPedido(valorPedido);
        pedido.setValorEntrega(BigDecimal.valueOf(5));
        pedido.setValorTotal(pedido.getValorPedido().add(pedido.getValorEntrega()));
        pedido.setTempoEstimado(TempoPedidoUtils.calcularTempo(itens));

        // Salvar Pedido
        PedidoEntity save = pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itens);

        // Evento inicial
        StatusEvent eventoInicial = StatusEvent.builder()
                .statusAntigo(StatusPedidoEnum.CRIADO)
                .statusNovo(StatusPedidoEnum.CRIADO)
                .horario(LocalDateTime.now())
                .pedido(save)
                .build();

        save.getTimeline().add(eventoInicial);

        return PedidoMapper.toResponse(save);
    }

    // READ ALL
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    // READ BY ID
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        return PedidoMapper.toResponse(pedido);
    }

    // READ BY STATUS
    public List<PedidoResponseDTO> listarPedidosPorStatus(StatusPedidoEnum status) {
        return pedidoRepository.findByStatus(status)
                .stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    // READ TIMELINE
    public List<StatusEventResponseDTO> mostrarTimeline(Long id) {

        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        return pedido.getTimeline()
                .stream()
                .map(StatusEventMapper::toDTO)
                .toList();
    }

    // UPDATE
    @Transactional
    public PedidoResponseDTO atualizarStatus(Long pedidoId, StatusPedidoEnum novoStatus) {

        // Buscar Cliente e Validar Cliente
        PedidoEntity pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        StatusPedidoEnum atual = pedido.getStatus();

        // Validar Transição
        if (!atual.podeIrPara(novoStatus)) {
            throw new BusinessException("Transição inválida: " + atual + " → " + novoStatus);
        }

        pedido.setStatus(novoStatus);

        // Criar Evento
        StatusEvent evento = StatusEvent.builder()
                .statusAntigo(atual)
                .statusNovo(novoStatus)
                .pedido(pedido)
                .build();

        // Garantir Criação Timeline
        if (pedido.getTimeline() == null) {
            pedido.setTimeline(new ArrayList<>());
        }

        pedido.getTimeline().add(evento);

        pedidoRepository.save(pedido);

        return PedidoMapper.toResponse(pedido);
    }

    // DELETE
    @Transactional
    public void deletarPedido(Long pedidoId) {

        PedidoEntity pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        pedidoRepository.delete(pedido);
    }
}
