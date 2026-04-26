package com.vtduarte.pizzahub.controller;

import com.vtduarte.pizzahub.database.model.ClienteEntity;
import com.vtduarte.pizzahub.dto.requests.ClienteRequestDTO;
import com.vtduarte.pizzahub.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // CREATE
    @PostMapping
    public ResponseEntity<ClienteEntity> cadastrarCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteEntity cliente = clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(201).body(cliente);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteEntity clienteAtualizado = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
