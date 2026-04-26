package com.vtduarte.pizzahub.controller;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import com.vtduarte.pizzahub.database.model.PedidoEntity;
import com.vtduarte.pizzahub.database.model.StatusEvent;
import com.vtduarte.pizzahub.dto.requests.PedidoRequestDTO;
import com.vtduarte.pizzahub.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // CREATE
    @PostMapping
    public ResponseEntity<PedidoEntity> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoEntity pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(201).body(pedido);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<PedidoEntity>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> buscarPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPedidoPorId(id));
    }

    // READ BY STATUS
    @GetMapping("/status")
    public ResponseEntity<List<PedidoEntity>> listarPedidosPorStatus(@RequestParam StatusPedidoEnum status) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorStatus(status));
    }

    // READ TIMELINE
    @GetMapping("/{id}/timeline")
    public ResponseEntity<List<StatusEvent>> mostrarTimeline(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.mostrarTimeline(id));
    }

    // UPDATE
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoEntity> atualizarStatus(@PathVariable Long id,
                                                        @RequestParam StatusPedidoEnum status) {
        PedidoEntity pedidoAtualizado = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
