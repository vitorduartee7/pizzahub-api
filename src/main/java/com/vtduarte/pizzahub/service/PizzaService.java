package com.vtduarte.pizzahub.service;

import com.vtduarte.pizzahub.database.model.PizzaEntity;
import com.vtduarte.pizzahub.database.repository.PizzaRepository;
import com.vtduarte.pizzahub.dto.requests.PizzaRequestDTO;
import com.vtduarte.pizzahub.exceptions.BusinessException;
import com.vtduarte.pizzahub.exceptions.ResourceNotFoundException;
import com.vtduarte.pizzahub.utils.FormatUtils;
import com.vtduarte.pizzahub.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    // CREATE
    @Transactional
    public PizzaEntity cadastrarPizza(PizzaRequestDTO dto) {

        // Formatar
        String nome = FormatUtils.formatarTexto(dto.getNome());
        String descricao = dto.getDescricao() != null ? dto.getDescricao().trim() : null;

        // Validar
        if (!ValidationUtils.validarNome(nome)) {
            throw new BusinessException("Nome inválido");
        }

        if (dto.getPrecoBase() == null || dto.getPrecoBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Preço inválido");
        }

        // Duplicidade
        pizzaRepository.findByNome(nome)
                .ifPresent(p -> {
                    throw new BusinessException("Pizza já cadastrada com esse nome");
                });

        // Criar Pizza
        PizzaEntity pizza = new PizzaEntity();
        pizza.setNome(nome);
        pizza.setPrecoBase(dto.getPrecoBase());
        pizza.setDescricao(descricao);
        pizza.setDisponivel(true);

        return pizzaRepository.save(pizza);
    }

    // READ ALL
    public List<PizzaEntity> listarPizzas() {
        return pizzaRepository.findAll();
    }

    // READ BY ID
    public PizzaEntity buscarPizzaPorId(Long id) {
        return pizzaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada"));
    }

    // UPDATE
    @Transactional
    public PizzaEntity atualizarPizza(Long id, PizzaRequestDTO dto) {

        // Buscar Pizza
        PizzaEntity pizza = buscarPizzaPorId(id);

        // Formatar
        String nome = FormatUtils.formatarTexto(dto.getNome());
        String descricao = dto.getDescricao() != null ? dto.getDescricao().trim() : null;

        // Validar
        if (!ValidationUtils.validarNome(nome)) {
            throw new BusinessException("Nome inválido");
        }

        if (dto.getPrecoBase() == null || dto.getPrecoBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Preço inválido");
        }

        // Validar Duplicidade
        pizzaRepository.findByNome(nome)
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new BusinessException("Já existe outra pizza com esse nome");
                });

        // Atualizar Campos
        pizza.setNome(nome);
        pizza.setDescricao(descricao);
        pizza.setPrecoBase(dto.getPrecoBase());

        return pizzaRepository.save(pizza);
    }

    // DELETE
    @Transactional
    public void deletarPizza(Long id) {

        // Buscar Pizza
        PizzaEntity pizza = buscarPizzaPorId(id);

        // Atualizar Pizza
        pizza.setDisponivel(false);

        pizzaRepository.save(pizza);
    }
}
