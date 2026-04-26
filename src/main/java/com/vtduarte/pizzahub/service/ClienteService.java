package com.vtduarte.pizzahub.service;

import com.vtduarte.pizzahub.client.ViaCepClient;
import com.vtduarte.pizzahub.database.model.ClienteEntity;
import com.vtduarte.pizzahub.database.model.EnderecoEntity;
import com.vtduarte.pizzahub.database.repository.ClienteRepository;
import com.vtduarte.pizzahub.database.repository.EnderecoRepository;
import com.vtduarte.pizzahub.dto.ClienteRequestDTO;
import com.vtduarte.pizzahub.dto.ViaCepResponseDTO;
import com.vtduarte.pizzahub.exceptions.BusinessException;
import com.vtduarte.pizzahub.exceptions.InvalidCepException;
import com.vtduarte.pizzahub.exceptions.ResourceNotFoundException;
import com.vtduarte.pizzahub.utils.FormatUtils;
import com.vtduarte.pizzahub.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepClient viaCepClient;

    // CREATE
    @Transactional
    public ClienteEntity cadastrarCliente(ClienteRequestDTO dto) {

        // Formatar Dados
        String email = FormatUtils.formatarEmail(dto.getEmail());
        String telefone = FormatUtils.formatarTelefone(dto.getTelefone());
        String cep = FormatUtils.formatarCep(dto.getCep());

        // Validar Cliente
        validarCliente(dto, email, telefone, cep);

        // Buscar Endereco no ViaCEP
        ViaCepResponseDTO cepResponseDTO = buscarEndereco(cep);

        // Montar e Salvar Endereco
        EnderecoEntity endereco = enderecoRepository.findByCep(cep)
                .orElseGet(() -> enderecoRepository.save(montarEndereco(cepResponseDTO)));

        // Criar Cliente
        ClienteEntity cliente = ClienteEntity.builder()
                .nome(dto.getNome())
                .email(email)
                .telefone(telefone)
                .endereco(endereco)
                .build();

        // Salvar Cliente
        return clienteRepository.save(cliente);
    }

    // READ ALL
    public List<ClienteEntity> listarClientes() {
        return clienteRepository.findAll();
    }

    // READ BY ID
    public ClienteEntity buscarClientePorId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    // UPDATE
    @Transactional
    public ClienteEntity atualizarCliente(Long clienteId, ClienteRequestDTO dto) {

        // Buscar Cliente
        ClienteEntity cliente = buscarClientePorId(clienteId);

        // Formatar Dados
        String email = FormatUtils.formatarEmail(dto.getEmail());
        String telefone = FormatUtils.formatarTelefone(dto.getTelefone());
        String cep = FormatUtils.formatarCep(dto.getCep());

        // Validar Cliente
        validarCliente(dto, email, telefone, cep,false, clienteId);

        // Buscar Endereco no ViaCEP
        ViaCepResponseDTO cepResponseDTO = buscarEndereco(cep);

        // Montar e Salvar Endereco
        EnderecoEntity endereco = enderecoRepository.findByCep(cep)
                .orElseGet(() -> enderecoRepository.save(montarEndereco(cepResponseDTO)));

        // ATUALIZAR DADOS
        cliente.setNome(dto.getNome());
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);
    }

    // DELETE
    @Transactional
    public void deletarCliente(Long clienteId) {

        // Buscar Cliente
        ClienteEntity cliente = buscarClientePorId(clienteId);

        // Deletar Cliente
        clienteRepository.delete(cliente);
    }

    // VALIDAR CLIENTE
    private void validarCliente(ClienteRequestDTO dto, String email, String telefone, String cep, boolean isCreate, Long id) {

        // Validar Nome
        if (!ValidationUtils.validarNome(dto.getNome())) {
            throw new BusinessException("Nome inválido");
        }

        // Validar Email
        if (!ValidationUtils.validarEmail(email)) {
            throw new BusinessException("Email inválido");
        }

        // Validar Telefone
        if (!ValidationUtils.validarTelefone(telefone)) {
            throw new BusinessException("Telefone inválido");
        }

        // Validar CEP
        if (!ValidationUtils.validarCep(cep)) {
            throw new InvalidCepException("CEP inválido");
        }

        // Validar Duplicidade Email
        clienteRepository.findByEmail(email)
                .filter(c -> isCreate || !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new BusinessException("Email já cadastrado");
                });

        // Validar Duplicidade Telefone
        clienteRepository.findByTelefone(telefone)
                .filter(c -> isCreate || !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new BusinessException("Telefone já cadastrado");
                });
    }

    // VALIDAR CLIENTE EXISTENTE
    private void validarCliente(ClienteRequestDTO dto, String email, String telefone, String cep) {
        validarCliente(dto, email, telefone, cep, true, null);
    }

    // BUSCAR ENDERECO
    private ViaCepResponseDTO buscarEndereco(String cep) {

        try {
            ViaCepResponseDTO response = viaCepClient.buscarEndereco(cep);

            if (response == null || response.hasErro()) {
                throw new InvalidCepException("CEP não encontrado");
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // MONTAR ENDERECO AUTOMATICO
    private EnderecoEntity montarEndereco(ViaCepResponseDTO cepResponseDTO) {

        return EnderecoEntity.builder()
                .cep(cepResponseDTO.getCep())
                .logradouro(cepResponseDTO.getLogradouro())
                .bairro(cepResponseDTO.getBairro())
                .localidade(cepResponseDTO.getLocalidade())
                .uf(cepResponseDTO.getUf())
                .complemento(cepResponseDTO.getComplemento())
                .build();
    }
}
