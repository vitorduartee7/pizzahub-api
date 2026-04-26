package com.vtduarte.pizzahub.controller;

import com.vtduarte.pizzahub.dto.requests.PizzaRequestDTO;
import com.vtduarte.pizzahub.dto.response.PizzaResponseDTO;
import com.vtduarte.pizzahub.service.PizzaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    // CREATE
    @PostMapping
    public ResponseEntity<PizzaResponseDTO> cadastrarPizza(@Valid @RequestBody PizzaRequestDTO dto) {
        PizzaResponseDTO pizza = pizzaService.cadastrarPizza(dto);
        return ResponseEntity.status(201).body(pizza);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<PizzaResponseDTO>> listarPizzas() {
        return ResponseEntity.ok(pizzaService.listarPizzas());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PizzaResponseDTO> buscarPizzaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pizzaService.buscarPizzaPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PizzaResponseDTO> atualizarPizza(@PathVariable Long id,
                                                      @Valid @RequestBody PizzaRequestDTO dto) {
        PizzaResponseDTO pizzaAtualizada = pizzaService.atualizarPizza(id, dto);
        return ResponseEntity.ok(pizzaAtualizada);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPizza(@PathVariable Long id) {
        pizzaService.deletarPizza(id);
        return ResponseEntity.noContent().build();
    }
}
