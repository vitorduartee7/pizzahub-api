package com.vtduarte.pizzahub.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 caracteres")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity endereco;
}

